package hytssk.ind.spring.base.auth.data.controllers

import hytssk.ind.spring.base.auth.data.trace.SpanWithContextGenerator
import hytssk.ind.spring.base.context.data.DataEntity
import hytssk.ind.spring.base.context.data.IDataRepository
import io.opentelemetry.api.OpenTelemetry
import jakarta.servlet.http.HttpServletRequest
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class DataController(
    private val dataRepository: IDataRepository,
    private val spanWithContextGenerator: SpanWithContextGenerator,
    openTelemetry: OpenTelemetry,
    restTemplateBuilder: RestTemplateBuilder,
) {
    private val tracer = openTelemetry.getTracer(this::class.simpleName ?: "auth-data")
    private val restTemplate = restTemplateBuilder.build()

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

    @PutMapping("/auth/data/{id}")
    fun putData(
        @PathVariable id: String,
        @RequestParam("flag") flag: Boolean,
        request: HttpServletRequest
    ): ResponseEntity<Unit> {
        val span = spanWithContextGenerator.generate(request, tracer, "putData")
        val entity = DataEntity(
            id = id,
            flag = flag,
        )
        dataRepository.save(entity)
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
