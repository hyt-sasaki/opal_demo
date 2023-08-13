package hytssk.ind.spring.base.app.controllers.message

import hytssk.ind.spring.base.app.trace.SpanWithContextGenerator
import hytssk.ind.spring.base.context.core.message.IMessageRepository
import hytssk.ind.spring.base.context.core.message.MessageDomainService
import hytssk.ind.spring.base.context.core.message.MessageEntity
import io.opentelemetry.api.OpenTelemetry
import io.opentelemetry.context.propagation.TextMapGetter
import jakarta.servlet.http.HttpServletRequest
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestParam
import java.util.Collections

@Controller
class MessageController(
    private val messageRepository: IMessageRepository,
    private val messageDomainService: MessageDomainService,
    private val spanGenerator: SpanWithContextGenerator,
    restTemplateBuilder: RestTemplateBuilder,
    openTelemetry: OpenTelemetry,
) {
    private val tracer = openTelemetry.getTracer(this::class.simpleName ?: "MessageController")
    private val restTemplate = restTemplateBuilder.build()

    @GetMapping("/messages/{id}")
    fun getMessage(@PathVariable id: String, request: HttpServletRequest): ResponseEntity<MessageDto> {
        val span = spanGenerator.generate(request, tracer, "getMessages")
        span.setAttribute("message_id", id)
        val result = messageRepository.findOneById(id)?.let {
            span.setAttribute("message_body", it.body)
            ResponseEntity.ok(it.toDto())
        }
        if (result == null) {
            span.setAttribute("error", "not found: id = $id")
            throw RuntimeException("Not Found")
        }
        span.end()
        return result
    }

    @PutMapping("/messages/{id}")
    fun putMessage(
        @PathVariable id: String,
        @RequestParam("body") body: String,
        request: HttpServletRequest
    ): ResponseEntity<Unit> {
        val span = spanGenerator.generate(request, tracer, "putMessage")
        messageDomainService.register(id, body)
        trigger()
        span.end()
        return ResponseEntity.noContent().build()
    }

    private fun trigger() {
        restTemplate.postForLocation(
            "http://localhost:7002/data/config",
            DataUpdate(
                entries = listOf(
                    DataUpdate.Entry(
                        url = "http://auth.data.envoy:3001/auth/data",
                        topics = listOf("policy_data"),
                        dst_path = "/custom_info",
                    )
                ),
                reason = "data is updated by user"
            ),
        )
    }

    private fun MessageEntity.toDto(): MessageDto {
        return MessageDto(id = id, body = body)
    }

    data class MessageDto(
        val id: String,
        val body: String,
    )

    @Suppress("ConstructorParameterNaming")
    private data class DataUpdate(
        val entries: List<Entry>,
        val reason: String,
    ) {
        data class Entry(
            val url: String,
            val topics: List<String>,
            val dst_path: String,
        )
    }
}

class HttpServletRequestTextMapGetter : TextMapGetter<HttpServletRequest> {
    override fun keys(carrier: HttpServletRequest): MutableIterable<String> {
        return Collections.list(carrier.headerNames)
    }

    override fun get(carrier: HttpServletRequest?, key: String): String? {
        return carrier?.getHeader(key)
    }

    companion object {
        val INSTANCE = HttpServletRequestTextMapGetter()
    }
}
