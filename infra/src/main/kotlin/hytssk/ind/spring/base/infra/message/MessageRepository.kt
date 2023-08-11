package hytssk.ind.spring.base.infra.message

import hytssk.ind.spring.base.context.core.message.IMessageRepository
import hytssk.ind.spring.base.context.core.message.MessageEntity
import org.springframework.stereotype.Repository

@Repository
class MessageRepository: IMessageRepository {
    override fun findOneById(id: String): MessageEntity {
        return MessageEntity(
            id = id,
            body = "hello",
        )
    }
}
