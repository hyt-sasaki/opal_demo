package hytssk.ind.spring.base.app.trace

import hytssk.ind.spring.base.app.controllers.message.HttpServletRequestTextMapGetter
import io.opentelemetry.api.OpenTelemetry
import io.opentelemetry.api.trace.Span
import io.opentelemetry.api.trace.Tracer
import io.opentelemetry.context.Context
import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Component

@Component
class SpanWithContextGenerator(
    private val openTelemetry: OpenTelemetry,
) {
    fun generate(request: HttpServletRequest, tracer: Tracer, spanName: String): Span {
        val context = openTelemetry.propagators.textMapPropagator
            .extract(Context.current(), request, HttpServletRequestTextMapGetter.INSTANCE)
        return tracer.spanBuilder(spanName)
            .setParent(context)
            .startSpan()
    }
}
