package hytssk.ind.spring.base.infra.message

import hytssk.ind.spring.base.context.core.message.IMessageRepository
import hytssk.ind.spring.base.context.core.message.MessageEntity
import org.springframework.stereotype.Repository

@Repository
class MessageRepository : IMessageRepository {
    var messages = arrayListOf<MessageEntity>()
    override fun findOneById(id: String): MessageEntity? {
        return messages.find { it.id == id }
    }

    override fun save(entity: MessageEntity): MessageEntity {
        val id = entity.id
        if (messages.any { it.id == id }) {
            messages[messages.indexOfFirst { it.id == id }] = entity
        } else {
            messages.add(entity)
        }
        return entity
    }
}
