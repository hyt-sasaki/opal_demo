package hytssk.ind.spring.base.app.config

import io.opentelemetry.api.OpenTelemetry
import io.opentelemetry.api.common.Attributes
import io.opentelemetry.api.trace.propagation.W3CTraceContextPropagator
import io.opentelemetry.context.propagation.ContextPropagators
import io.opentelemetry.exporter.otlp.logs.OtlpGrpcLogRecordExporter
import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter
import io.opentelemetry.sdk.OpenTelemetrySdk
import io.opentelemetry.sdk.logs.SdkLoggerProvider
import io.opentelemetry.sdk.logs.export.BatchLogRecordProcessor
import io.opentelemetry.sdk.resources.Resource
import io.opentelemetry.sdk.trace.SdkTracerProvider
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor
import io.opentelemetry.semconv.resource.attributes.ResourceAttributes
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenTelemetryConfig {
    @Bean
    fun openTelemetry(): OpenTelemetry {
        val resource = Resource.getDefault()
            .merge(Resource.create(Attributes.of(ResourceAttributes.SERVICE_NAME, "app-service")))
        val traceProvider = SdkTracerProvider.builder()
            .addSpanProcessor(BatchSpanProcessor.builder(OtlpGrpcSpanExporter.builder().build()).build())
            .setResource(resource)
            .build()
        val loggerProvider = SdkLoggerProvider.builder()
            .addLogRecordProcessor(BatchLogRecordProcessor.builder(OtlpGrpcLogRecordExporter.builder().build()).build())
            .setResource(resource)
            .build()
        return OpenTelemetrySdk.builder()
            .setTracerProvider(traceProvider)
            .setLoggerProvider(loggerProvider)
            .setPropagators(ContextPropagators.create(W3CTraceContextPropagator.getInstance()))
            .buildAndRegisterGlobal()
    }
}
