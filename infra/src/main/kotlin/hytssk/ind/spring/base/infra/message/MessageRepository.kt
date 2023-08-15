package hytssk.ind.spring.base.infra.message

import hytssk.ind.spring.base.context.core.message.IMessageRepository
import hytssk.ind.spring.base.context.core.message.MessageEntity
import hytssk.ind.spring.base.jooq.generated.tables.records.MessagesRecord
import hytssk.ind.spring.base.jooq.generated.tables.references.MESSAGES
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class MessageRepository(
    private val jooq: DSLContext,
) : IMessageRepository {
    override fun findOneById(id: String): MessageEntity? {
        return jooq.selectFrom(MESSAGES)
            .where(MESSAGES.ID.eq(id.toLongOrNull()))
            .fetchOne { it.toEntity() }
    }

    @Transactional
    override fun save(entity: MessageEntity): MessageEntity {
        val identifyCondition = MESSAGES.ID.eq(entity.id.toLongOrNull())
        val existingRecord = jooq.selectFrom(MESSAGES)
            .where(identifyCondition)
            .forUpdate()
            .fetchOne()
        if (existingRecord != null) {
            jooq.update(MESSAGES)
                .set(
                    existingRecord.apply {
                        body = entity.body
                    }
                )
                .where(identifyCondition)
                .execute()
            return entity
        } else {
            val newId = jooq.insertInto(MESSAGES)
                .set(MessagesRecord(id = entity.id.toLong(), body = entity.body))
                .returning()
                .fetchOne { it.id }
            return MessageEntity(
                id = newId.toString(),
                body = entity.body,
            )
        }
    }

    private fun MessagesRecord.toEntity(): MessageEntity {
        return MessageEntity(
            id = id.toString(),
            body = body,
        )
    }
}
