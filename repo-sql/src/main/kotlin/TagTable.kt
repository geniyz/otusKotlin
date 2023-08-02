package site.geniyz.otus.backend.repo.sql

import kotlinx.datetime.Clock
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentTimestamp
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import site.geniyz.otus.common.models.*

object TagTable : Table("tags") {
    val id   = varchar("id", 128)
    val name = varchar("name", 128).uniqueIndex()
    val code = varchar("code", 128).uniqueIndex()
    val lock = varchar("lock", 128)
    val createdAt = timestamp("createdAt").defaultExpression( CurrentTimestamp() )
    val updatedAt = timestamp("updatedAt").defaultExpression( CurrentTimestamp() )

    override val primaryKey = PrimaryKey(id)

    fun from(res: InsertStatement<Number>) = AppTag(
        id        = AppTagId(res[id].toString()),
        name      = res[name],
        code      = res[code],
        lock      = AppLock(res[lock]),
        createdAt = res[createdAt],
        updatedAt = res[updatedAt],
    )

    fun from(res: ResultRow) = AppTag(
        id        = AppTagId(res[id].toString()),
        name      = res[name],
        code      = res[code],
        lock      = AppLock(res[lock]),
        createdAt = res[createdAt],
        updatedAt = res[updatedAt],
    )

    fun to(it: UpdateBuilder<*>, o: AppTag, randomUuid: () -> String) {
        it[id] = o.id.takeIf { it != AppTagId.NONE }?.asString() ?: randomUuid()
        it[name] = o.name
        it[code] = o.code

        it[lock] = o.lock.takeIf { it != AppLock.NONE }?.asString() ?: randomUuid()

        it[createdAt] = o.createdAt
        it[updatedAt] = Clock.System.now()
    }

}
