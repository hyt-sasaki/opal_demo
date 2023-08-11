package hytssk.ind.spring.base.infra.message

import hytssk.ind.spring.base.context.core.message.MessageEntity
import hytssk.ind.spring.base.infra.InfraScanConfig
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.assertEquals

@SpringBootTest(classes = [InfraScanConfig::class])
class MessageRepositoryTest @Autowired constructor(
    private val repository: MessageRepository,
) {
    @Test
    fun test() {
        assertEquals(
            expected = MessageEntity(
                id = "1",
                body = "hello"
            ),
            actual = repository.findOneById("1")
        )
    }
}
