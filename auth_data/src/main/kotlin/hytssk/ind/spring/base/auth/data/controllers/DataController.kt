package hytssk.ind.spring.base.auth.data.controllers

import io.opentelemetry.api.OpenTelemetry
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class DataController(
    openTelemetry: OpenTelemetry,
) {
    private val tracer = openTelemetry.getTracer(this::class.simpleName ?: "auth-data")

    @GetMapping("/auth/data")
    fun getData(): ResponseEntity<List<DataDto>> {
        val span = tracer.spanBuilder("getDataSpan").startSpan()
        val result = ResponseEntity.ok(
            listOf(
                DataDto("1", true),
                DataDto("2", false),
                DataDto("3", true),
            )
        )
        span.end()
        return result
    }

    data class DataDto(
        val id: String,
        val flag: Boolean,
    )
}
