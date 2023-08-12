package hytssk.ind.spring.base.auth.data.trace

import io.opentelemetry.context.propagation.TextMapGetter
import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Component
import java.util.*

@Component
class HttpServletRequestTextMapGetter : TextMapGetter<HttpServletRequest> {
    override fun keys(carrier: HttpServletRequest): MutableIterable<String> {
        return Collections.list(carrier.headerNames)
    }

    override fun get(carrier: HttpServletRequest?, key: String): String? {
        return carrier?.getHeader(key)
    }
}
