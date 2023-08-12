package hytssk.ind.spring.base.app.controllers.message

import hytssk.ind.spring.base.app.trace.SpanWithContextGenerator
import hytssk.ind.spring.base.context.core.message.IMessageRepository
import hytssk.ind.spring.base.context.core.message.MessageEntity
import io.opentelemetry.api.OpenTelemetry
import io.opentelemetry.context.propagation.TextMapGetter
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import java.util.Collections

@Controller
class MessageController(
    private val messageRepository: IMessageRepository,
    private val spanGenerator: SpanWithContextGenerator,
    openTelemetry: OpenTelemetry,
) {
    private val tracer = openTelemetry.getTracer(this::class.simpleName ?: "MessageController")

    @GetMapping("/messages/{id}")
    fun getMessages(@PathVariable id: String, request: HttpServletRequest): ResponseEntity<MessageDto> {
        val span = spanGenerator.generate(request, tracer, "getMessages")
        span.setAttribute("message_id", id)
        val result = messageRepository.findOneById(id).let {
            span.setAttribute("message_body", it.body)
            ResponseEntity.ok(it.toDto())
        }
        span.end()
        return result
    }

    private fun MessageEntity.toDto(): MessageDto {
        return MessageDto(id = id, body = body)
    }

    data class MessageDto(
        val id: String,
        val body: String,
    )
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
