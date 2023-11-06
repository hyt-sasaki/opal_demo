package hytssk.ind.spring.base.context.core.message

import hytssk.ind.spring.base.context.data.DataEntity
import hytssk.ind.spring.base.context.data.IDataRepository
import org.springframework.stereotype.Service

@Service
class MessageDomainService(
    private val messageRepository: IMessageRepository,
    private val dataRepository: IDataRepository,
) {
    fun register(id: String, body: String) {
        val entity = MessageEntity(id = id, body = body)
        messageRepository.save(entity)
        val dataEntity = DataEntity(id = id, flag = id.toInt() % 2 == 0)
        dataRepository.save(dataEntity)
    }
}
