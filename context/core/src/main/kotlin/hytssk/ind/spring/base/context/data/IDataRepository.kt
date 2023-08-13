package hytssk.ind.spring.base.context.data

interface IDataRepository {
    fun findAll(): List<DataEntity>
    fun save(entity: DataEntity): DataEntity
}
