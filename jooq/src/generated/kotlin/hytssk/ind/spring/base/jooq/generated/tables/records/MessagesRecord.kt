/*
 * This file is generated by jOOQ.
 */
package hytssk.ind.spring.base.jooq.generated.tables.records


import hytssk.ind.spring.base.jooq.generated.tables.Messages

import java.time.LocalDateTime

import org.jooq.Field
import org.jooq.Record1
import org.jooq.Record4
import org.jooq.Row4
import org.jooq.impl.UpdatableRecordImpl


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
open class MessagesRecord private constructor() : UpdatableRecordImpl<MessagesRecord>(Messages.MESSAGES), Record4<Long?, String?, LocalDateTime?, LocalDateTime?> {

    open var id: Long
        set(value): Unit = set(0, value)
        get(): Long = get(0) as Long

    open var body: String
        set(value): Unit = set(1, value)
        get(): String = get(1) as String

    open var createdAt: LocalDateTime?
        set(value): Unit = set(2, value)
        get(): LocalDateTime? = get(2) as LocalDateTime?

    open var updatedAt: LocalDateTime?
        set(value): Unit = set(3, value)
        get(): LocalDateTime? = get(3) as LocalDateTime?

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    override fun key(): Record1<Long?> = super.key() as Record1<Long?>

    // -------------------------------------------------------------------------
    // Record4 type implementation
    // -------------------------------------------------------------------------

    override fun fieldsRow(): Row4<Long?, String?, LocalDateTime?, LocalDateTime?> = super.fieldsRow() as Row4<Long?, String?, LocalDateTime?, LocalDateTime?>
    override fun valuesRow(): Row4<Long?, String?, LocalDateTime?, LocalDateTime?> = super.valuesRow() as Row4<Long?, String?, LocalDateTime?, LocalDateTime?>
    override fun field1(): Field<Long?> = Messages.MESSAGES.ID
    override fun field2(): Field<String?> = Messages.MESSAGES.BODY
    override fun field3(): Field<LocalDateTime?> = Messages.MESSAGES.CREATED_AT
    override fun field4(): Field<LocalDateTime?> = Messages.MESSAGES.UPDATED_AT
    override fun component1(): Long = id
    override fun component2(): String = body
    override fun component3(): LocalDateTime? = createdAt
    override fun component4(): LocalDateTime? = updatedAt
    override fun value1(): Long = id
    override fun value2(): String = body
    override fun value3(): LocalDateTime? = createdAt
    override fun value4(): LocalDateTime? = updatedAt

    override fun value1(value: Long?): MessagesRecord {
        set(0, value)
        return this
    }

    override fun value2(value: String?): MessagesRecord {
        set(1, value)
        return this
    }

    override fun value3(value: LocalDateTime?): MessagesRecord {
        set(2, value)
        return this
    }

    override fun value4(value: LocalDateTime?): MessagesRecord {
        set(3, value)
        return this
    }

    override fun values(value1: Long?, value2: String?, value3: LocalDateTime?, value4: LocalDateTime?): MessagesRecord {
        this.value1(value1)
        this.value2(value2)
        this.value3(value3)
        this.value4(value4)
        return this
    }

    /**
     * Create a detached, initialised MessagesRecord
     */
    constructor(id: Long, body: String, createdAt: LocalDateTime? = null, updatedAt: LocalDateTime? = null): this() {
        this.id = id
        this.body = body
        this.createdAt = createdAt
        this.updatedAt = updatedAt
        resetChangedOnNotNull()
    }
}
