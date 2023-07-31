package site.geniyz.otus.backend.repo.sql

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import site.geniyz.otus.common.models.*

object TagTable : Table("tags") {
    val id   = varchar("id", 128)
    val name = varchar("name", 128)
    val code = varchar("code", 128)
    val lock = varchar("lock", 128)

    override val primaryKey = PrimaryKey(id)

    fun from(res: InsertStatement<Number>) = AppTag(
        id = AppTagId(res[id].toString()),
        name = res[name],
        code = res[code],
        lock = AppLock(res[lock])
    )

    fun from(res: ResultRow) = AppTag(
        id = AppTagId(res[id].toString()),
        name = res[name],
        code = res[code],
        lock = AppLock(res[lock])
    )

    fun to(it: UpdateBuilder<*>, o: AppTag, randomUuid: () -> String) {
        it[id] = o.id.takeIf { it != AppTagId.NONE }?.asString() ?: randomUuid()
        it[name] = o.name
        it[code] = o.code
        it[lock] = o.lock.takeIf { it != AppLock.NONE }?.asString() ?: randomUuid()
    }

}
