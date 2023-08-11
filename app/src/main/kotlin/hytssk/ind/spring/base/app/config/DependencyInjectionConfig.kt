package hytssk.ind.spring.base.app.config

import hytssk.ind.spring.base.infra.InfraScanConfig
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(value = [InfraScanConfig::class])
class DependencyInjectionConfig
