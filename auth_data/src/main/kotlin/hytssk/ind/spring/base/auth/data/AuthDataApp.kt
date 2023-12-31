package hytssk.ind.spring.base.auth.data

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AuthDataApp

@Suppress("SpreadOperator")
fun main(args: Array<String>) {
    runApplication<AuthDataApp>(*args)
}
