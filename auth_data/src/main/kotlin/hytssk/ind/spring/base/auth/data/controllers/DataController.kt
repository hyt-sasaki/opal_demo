package hytssk.ind.spring.base.auth.data.controllers

import hytssk.ind.spring.base.auth.data.trace.SpanWithContextGenerator
import io.opentelemetry.api.OpenTelemetry
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class DataController(
    private val spanWithContextGenerator: SpanWithContextGenerator,
    openTelemetry: OpenTelemetry,
) {
    private val tracer = openTelemetry.getTracer(this::class.simpleName ?: "auth-data")
    var data = arrayListOf(
        DataDto("1", true),
        DataDto("2", false),
        DataDto("3", true),
    )

    @GetMapping("/auth/data")
    fun getData(request: HttpServletRequest): ResponseEntity<List<DataDto>> {
        val span = spanWithContextGenerator.generate(request, tracer, "getData")
        val result = ResponseEntity.ok(data.toList())
        span.end()
        return result
    }

    @PutMapping("/auth/data/{id}")
    fun putData(
        @PathVariable id: String,
        @RequestParam("flag") flag: Boolean,
        request: HttpServletRequest
    ): ResponseEntity<Unit> {
        val span = spanWithContextGenerator.generate(request, tracer, "putData")
        data[data.indexOfFirst { it.id == id }] = DataDto(
            id = id,
            flag = flag,
        )
        span.end()
        return ResponseEntity.noContent().build()
    }

    data class DataDto(
        val id: String,
        val flag: Boolean,
    )
}
