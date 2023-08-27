package hytssk.ind.spring.base.app.controllers.message

import hytssk.ind.spring.base.context.core.message.IMessageRepository
import hytssk.ind.spring.base.context.core.message.MessageDomainService
import hytssk.ind.spring.base.context.core.message.MessageEntity
import io.opentelemetry.api.baggage.Baggage
import io.opentelemetry.api.common.AttributeKey
import io.opentelemetry.api.common.Attributes
import io.opentelemetry.api.trace.Span
import io.opentelemetry.context.Context
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.client.getForObject

@Controller
class MessageController(
    private val messageRepository: IMessageRepository,
    private val messageDomainService: MessageDomainService,
    restTemplateBuilder: RestTemplateBuilder,
) {
    private val restTemplate = restTemplateBuilder.build()

    @Suppress("TooGenericExceptionThrown")
    @GetMapping("/messages/{id}")
    fun getMessage(@PathVariable id: String): ResponseEntity<MessageDto> {
        val span = Span.current()
        span.setAttribute("message_id", id)
        callOpa()
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
        span.addEvent(
            "trigger data update",
            Attributes.of(
                AttributeKey.stringKey("topic"),
                topic,
                AttributeKey.stringKey("dst_path"),
                dstPath,
            )
        )
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

    private fun callOpa() {
        Baggage.builder()
            .put("user", "hytssk")
            .build()
            .storeInContext(Context.current())
        val span = Span.current()
        span.addEvent("call opa")
        val response = restTemplate.getForObject<OpaResult>(
            "http://localhost:8182/v1/data/agg/info_count?instrument=true&metrics=true&explains=fails&provenance=true"
        )
        span.addEvent(
            "call opa success",
            Attributes.of(
                AttributeKey.stringKey("data count"),
                response.result.toString(),
            )
        )
    }

    @Suppress("ConstructorParameterNaming")
    private data class OpaResult(
        val decision_id: String,
        val result: Int,
    )

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
