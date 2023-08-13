package hytssk.ind.spring.base.app.config

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.ComponentScans
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScans(
    ComponentScan("hytssk.ind.spring.base.infra"),
    ComponentScan("hytssk.ind.spring.base.context"),
)
class DependencyInjectionConfig
