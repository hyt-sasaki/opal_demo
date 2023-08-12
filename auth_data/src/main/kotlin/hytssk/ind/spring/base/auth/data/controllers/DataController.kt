package hytssk.ind.spring.base.auth.data.controllers

import hytssk.ind.spring.base.auth.data.trace.SpanWithContextGenerator
import io.opentelemetry.api.OpenTelemetry
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class DataController(
    private val spanWithContextGenerator: SpanWithContextGenerator,
    openTelemetry: OpenTelemetry,
) {
    private val tracer = openTelemetry.getTracer(this::class.simpleName ?: "auth-data")

    @GetMapping("/auth/data")
    fun getData(request: HttpServletRequest): ResponseEntity<List<DataDto>> {
        val span = spanWithContextGenerator.generate(request, tracer, "getData")
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
