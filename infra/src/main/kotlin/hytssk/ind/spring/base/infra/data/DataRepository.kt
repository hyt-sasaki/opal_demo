package hytssk.ind.spring.base.infra.data

import hytssk.ind.spring.base.context.data.DataEntity
import hytssk.ind.spring.base.context.data.IDataRepository
import hytssk.ind.spring.base.jooq.generated.tables.records.DataRecord
import hytssk.ind.spring.base.jooq.generated.tables.references.DATA
import io.opentelemetry.api.trace.Span
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class DataRepository(
    private val jooq: DSLContext,
) : IDataRepository {
    var data = arrayListOf<DataEntity>()

    override fun findAll(): List<DataEntity> {
        return jooq.selectFrom(DATA)
            .fetch { it.toEntity() }
    }

    @Transactional
    override fun save(entity: DataEntity): DataEntity {
        val identifyCondition = DATA.ID.eq(entity.id.toLongOrNull())
        val existingRecord = jooq.selectFrom(DATA)
            .where(identifyCondition)
            .forUpdate()
            .fetchOne()
        val span = Span.current()
        if (existingRecord != null) {
            span.setAttribute("record_id", entity.id)
            span.setAttribute("save_type", "update")
            jooq.update(DATA)
                .set(
                    existingRecord.apply {
                        flag = entity.flag
                    }
                )
                .where(identifyCondition)
                .execute()
            return entity
        } else {
            val newId = jooq.insertInto(DATA)
                .set(DataRecord(id = entity.id.toLong(), flag = entity.flag))
                .execute()
            span.setAttribute("record_id", newId.toString())
            span.setAttribute("save_type", "create")
            return DataEntity(
                id = newId.toString(),
                flag = entity.flag,
            )
        }
    }

    private fun DataRecord.toEntity(): DataEntity {
        return DataEntity(
            id = id.toString(),
            flag = flag
        )
    }
}
