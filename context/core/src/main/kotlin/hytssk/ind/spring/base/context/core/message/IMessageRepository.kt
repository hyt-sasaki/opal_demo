package hytssk.ind.spring.base.context.core.message

interface IMessageRepository {
    fun findOneById(id: String): MessageEntity?
    fun save(entity: MessageEntity): MessageEntity
}
