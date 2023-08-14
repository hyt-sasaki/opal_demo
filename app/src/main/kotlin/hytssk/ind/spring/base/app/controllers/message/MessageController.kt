package hytssk.ind.spring.base.app.controllers.message

import hytssk.ind.spring.base.context.core.message.IMessageRepository
import hytssk.ind.spring.base.context.core.message.MessageDomainService
import hytssk.ind.spring.base.context.core.message.MessageEntity
import io.opentelemetry.api.trace.Span
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class MessageController(
    private val messageRepository: IMessageRepository,
    private val messageDomainService: MessageDomainService,
    restTemplateBuilder: RestTemplateBuilder,
) {
    private val restTemplate = restTemplateBuilder.build()

    @GetMapping("/messages/{id}")
    fun getMessage(@PathVariable id: String): ResponseEntity<MessageDto> {
        val span = Span.current()
        span.setAttribute("message_id", id)
        val result = messageRepository.findOneById(id)?.let {
            span.setAttribute("message_body", it.body)
            ResponseEntity.ok(it.toDto())
        }
        if (result == null) {
            span.setAttribute("error", "not found: id = $id")
            throw RuntimeException("Not Found")
        }
        return result
    }

    @PutMapping("/messages/{id}")
    fun putMessage(
        @PathVariable id: String,
        @RequestParam("body") body: String,
    ): ResponseEntity<Unit> {
        messageDomainService.register(id, body)
        trigger()
        return ResponseEntity.noContent().build()
    }

    private fun trigger() {
        val span = Span.current()
        val spanContext = span.spanContext
        val topic = "policy_data"
        val dstPath = "/custom_info"
        span.setAttribute("topic", topic)
        span.setAttribute("dst_path", dstPath)
        restTemplate.postForLocation(
            "http://localhost:3002/data/config",
            DataUpdate(
                entries = listOf(
                    DataUpdate.Entry(
                        url = "http://auth.data.envoy:3001/auth/data",
                        topics = listOf(topic),
                        dst_path = dstPath,
                    )
                ),
                reason = "TraceId:${spanContext.traceId}, SpanId:${spanContext.spanId}"
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
