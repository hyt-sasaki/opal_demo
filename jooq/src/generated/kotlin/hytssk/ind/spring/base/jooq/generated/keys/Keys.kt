/*
 * This file is generated by jOOQ.
 */
package hytssk.ind.spring.base.jooq.generated.keys


import hytssk.ind.spring.base.jooq.generated.tables.Data
import hytssk.ind.spring.base.jooq.generated.tables.Messages
import hytssk.ind.spring.base.jooq.generated.tables.records.DataRecord
import hytssk.ind.spring.base.jooq.generated.tables.records.MessagesRecord

import org.jooq.UniqueKey
import org.jooq.impl.DSL
import org.jooq.impl.Internal



// -------------------------------------------------------------------------
// UNIQUE and PRIMARY KEY definitions
// -------------------------------------------------------------------------

val KEY_DATA_PRIMARY: UniqueKey<DataRecord> = Internal.createUniqueKey(Data.DATA, DSL.name("KEY_data_PRIMARY"), arrayOf(Data.DATA.ID), true)
val KEY_MESSAGES_PRIMARY: UniqueKey<MessagesRecord> = Internal.createUniqueKey(Messages.MESSAGES, DSL.name("KEY_messages_PRIMARY"), arrayOf(Messages.MESSAGES.ID), true)
