package site.geniyz.otus.backend.repo.sql

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentTimestamp
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateBuilder

import site.geniyz.otus.common.models.*

object ObjTable : Table("objects") {
    val id        = varchar("id", 128)
    val name      = varchar("name", 128)
    val content   = varchar("content", 4000)
    val author    = varchar("author", 128)
    val objType   = enumeration("objType", AppObjType::class)
    val lock      = varchar("lock", 128)
    val createdAt = timestamp("createdAt").defaultExpression( CurrentTimestamp() )
    val updatedAt = timestamp("updatedAt").defaultExpression( CurrentTimestamp() )

    override val primaryKey = PrimaryKey(id)

    fun from(res: InsertStatement<Number>) = AppObj(
        id        = AppObjId(res[id].toString()),
        name      = res[name],
        content   = res[content],
        authorId  = AppUserId(res[author].toString()),
        objType   = res[objType],
        lock      = AppLock(res[lock]),
        createdAt = res[createdAt],
        updatedAt = res[updatedAt],
    )

    fun from(res: ResultRow) = AppObj(
        id        = AppObjId(res[id].toString()),
        name      = res[name],
        content   = res[content],
        authorId  = AppUserId(res[author].toString()),
        objType   = res[objType],
        lock      = AppLock(res[lock]),
        createdAt = res[createdAt],
        updatedAt = res[updatedAt],
    )

    fun to(it: UpdateBuilder<*>, o: AppObj, randomUuid: () -> String) {
        it[id]        = o.id.takeIf { it != AppObjId.NONE }?.asString() ?: randomUuid()
        it[name]      = o.name
        it[content]   = o.content
        it[author]    = o.authorId.asString()
        it[objType]   = o.objType
        it[lock]      = o.lock.takeIf { it != AppLock.NONE }?.asString() ?: randomUuid()

        it[createdAt] = o.createdAt
        it[updatedAt] = CurrentTimestamp()
    }

}
