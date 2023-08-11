package site.geniyz.otus.backend.repo.sql

import org.jetbrains.exposed.sql.Table

object LnkTable : Table("objects_tags") {
    val id  = varchar("id", 128)
    val obj = reference("obj", ObjTable.id)
    val tag = reference("tag", TagTable.id)

    override val primaryKey = PrimaryKey(id)

    val ui = index(true, obj, tag) // чтоб не дублировались связи

}
