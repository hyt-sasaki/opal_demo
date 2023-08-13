package hytssk.ind.spring.base.auth.data.controllers

import hytssk.ind.spring.base.auth.data.trace.SpanWithContextGenerator
import hytssk.ind.spring.base.context.data.DataEntity
import hytssk.ind.spring.base.context.data.IDataRepository
import io.opentelemetry.api.OpenTelemetry
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class DataController(
    private val dataRepository: IDataRepository,
    private val spanWithContextGenerator: SpanWithContextGenerator,
    openTelemetry: OpenTelemetry,
) {
    private val tracer = openTelemetry.getTracer(this::class.simpleName ?: "auth-data")

    @GetMapping("/auth/data")
    fun getData(request: HttpServletRequest): ResponseEntity<List<DataDto>> {
        val span = spanWithContextGenerator.generate(request, tracer, "getData")
        val result = dataRepository.findAll()
            .map { it.toDto() }
            .let {
                ResponseEntity.ok(it)
            }
        span.end()
        return result
    }

    data class DataDto(
        val id: String,
        val flag: Boolean,
    )

    private fun DataEntity.toDto(): DataDto {
        return DataDto(
            id = id,
            flag = flag,
        )
    }
}
