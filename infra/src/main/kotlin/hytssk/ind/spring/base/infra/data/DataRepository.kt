package hytssk.ind.spring.base.infra.data

import hytssk.ind.spring.base.context.data.DataEntity
import hytssk.ind.spring.base.context.data.IDataRepository
import org.springframework.stereotype.Repository

@Repository
class DataRepository : IDataRepository {
    var data = arrayListOf<DataEntity>()

    override fun findAll(): List<DataEntity> {
        return data
    }

    override fun save(entity: DataEntity): DataEntity {
        val id = entity.id
        if (data.any { it.id == id }) {
            data[data.indexOfFirst { it.id == id }] = entity
        } else {
            data.add(entity)
        }
        return entity
    }
}
