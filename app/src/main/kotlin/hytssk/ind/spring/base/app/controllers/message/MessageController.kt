package hytssk.ind.spring.base.app.controllers.message

import hytssk.ind.spring.base.context.core.message.IMessageRepository
import hytssk.ind.spring.base.context.core.message.MessageEntity
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class MessageController(
    private val messageRepository: IMessageRepository
) {
    @GetMapping("/message")
    fun getMessage(): ResponseEntity<MessageDto> {
        return messageRepository.findOneById("1").let {
            ResponseEntity.ok(it.toDto())
        }
    }

    private fun MessageEntity.toDto(): MessageDto {
        return MessageDto(id = id, body = body)
    }

    data class MessageDto(
        val id: String,
        val body: String,
    )
}
