package site.geniyz.otus.backend.repo.sql

import com.benasher44.uuid.uuid4
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import site.geniyz.otus.common.helpers.*
import site.geniyz.otus.common.models.*
import site.geniyz.otus.common.repo.*

class RepoSQL(
    properties: SqlProperties,

    initObjs:  List<AppObj> = emptyList(),
    initTags:  List<AppTag> = emptyList(),
    initLinks: Map<String, List<String>> = emptyMap(),

    val randomUuid: () -> String = { uuid4().toString() },
) : IRepository {

    init {
        Database.connect(
            url      = properties.url,
            user     = properties.user,
            password = properties.password,
        )

        transaction {
            if (properties.dropDatabase){
                SchemaUtils.drop(LnkTable)
                SchemaUtils.drop(TagTable)
                SchemaUtils.drop(ObjTable)
            }
            SchemaUtils.create(ObjTable)
            SchemaUtils.create(TagTable)
            SchemaUtils.create(LnkTable)

            initObjs.forEach { createObj(it) }
            initTags.forEach { createTag(it) }
            initLinks.forEach { l ->
                l.value.forEach { saveLnk(l.key, it) }
            }

        }
    }

    private fun <T> transactionWrapper(block: () -> T, handle: (Exception) -> T): T =
        try {
            transaction {
                block()
            }
        } catch (e: Exception) {
            handle(e)
        }




    // Objects
    private fun createObj(o: AppObj): AppObj {
        val res = ObjTable.insert {
            to(it, o, randomUuid)
        }
        return ObjTable.from(res)
    }
    override suspend fun createObj(rq: DbObjRequest): DbObjResponse = transactionWrapper(
        { DbObjResponse.success(createObj(rq.obj)) },
        { DbObjResponse.error(it.asAppError())     },
    )

    private fun readObj(id: AppObjId): DbObjResponse {
        val res = ObjTable.select {
            ObjTable.id eq id.asString()
        }.singleOrNull() ?: return DbObjResponse.errorNotFound
        return DbObjResponse.success(ObjTable.from(res))
    }

    override suspend fun readObj(rq: DbObjIdRequest): DbObjResponse = transactionWrapper(
        { readObj(rq.id) },
        { DbObjResponse.error(it.asAppError())     },
    )

    private fun updateObj(
        id: AppObjId,
        lock: AppLock,
        block: (AppObj) -> DbObjResponse
    ): DbObjResponse =
        transactionWrapper({
            if (id == AppObjId.NONE) return@transactionWrapper DbObjResponse.errorEmptyId

            val current = ObjTable.select { ObjTable.id eq id.asString() }
                .firstOrNull()
                ?.let { ObjTable.from(it) }

            when {
                current == null -> DbObjResponse.errorNotFound
                current.lock != lock -> DbObjResponse.errorConcurrent(lock, current)
                else -> block(current)
            }
        }, { DbObjResponse.error(it.asAppError()) })

    override suspend fun updateObj(rq: DbObjRequest): DbObjResponse =
        updateObj(rq.obj.id, rq.obj.lock) {
            ObjTable.update({ ObjTable.id eq rq.obj.id.asString() }) {
                to(it, rq.obj, randomUuid)
            }
            readObj(rq.obj.id)
        }

    override suspend fun deleteObj(rq: DbObjIdRequest): DbObjResponse = updateObj(rq.id, rq.lock) {
        ObjTable.deleteWhere { ObjTable.id eq rq.id.asString() }
        DbObjResponse.success(it)
    }

    override suspend fun searchObj(rq: DbObjFilterRequest): DbObjsResponse =
        transactionWrapper({
            val res = ObjTable.select {
                buildList {
                    add(Op.TRUE)
                    if (rq.ownerId != AppUserId.NONE) {
                        add(ObjTable.author eq rq.ownerId.asString())
                    }
                    if (rq.searchString.isNotBlank()) {
                        add(
                            (ObjTable.name like "%${rq.searchString}%")
                                    or (ObjTable.content like "%${rq.searchString}%")
                        )
                    }
                }.reduce { a, b -> a and b }
            }
            DbObjsResponse(data = res.map { ObjTable.from(it) }, isSuccess = true)
        }, {
            DbObjsResponse.error(it.asAppError())
        })




    // Tags
    private fun createTag(o: AppTag): AppTag {
        val res = TagTable.insert {
            to(it, o, randomUuid)
        }
        return TagTable.from(res)
    }
    override suspend fun createTag(rq: DbTagRequest): DbTagResponse = transactionWrapper(
        { DbTagResponse.success(createTag(rq.tag)) },
        { DbTagResponse.error(it.asAppError())     },
    )

    private fun readTag(id: AppTagId): DbTagResponse {
        val res = TagTable.select {
            TagTable.id eq id.asString()
        }.singleOrNull() ?: return DbTagResponse.errorNotFound
        return DbTagResponse.success(TagTable.from(res))
    }

    override suspend fun readTag(rq: DbTagIdRequest): DbTagResponse = transactionWrapper(
        { readTag(rq.id) },
        { DbTagResponse.error(it.asAppError())     },
    )

    private fun updateTag(
        id: AppTagId,
        lock: AppLock,
        block: (AppTag) -> DbTagResponse
    ): DbTagResponse =
        transactionWrapper({
            if (id == AppTagId.NONE) return@transactionWrapper DbTagResponse.errorEmptyId

            val current = TagTable.select { TagTable.id eq id.asString() }
                .firstOrNull()
                ?.let { TagTable.from(it) }

            when {
                current == null -> DbTagResponse.errorNotFound
                current.lock != lock -> DbTagResponse.errorConcurrent(lock, current)
                else -> block(current)
            }
        }, { DbTagResponse.error(it.asAppError()) })

    override suspend fun updateTag(rq: DbTagRequest): DbTagResponse =
        updateTag(rq.tag.id, rq.tag.lock) {
            TagTable.update({ ObjTable.id eq rq.tag.id.asString() }) {
                to(it, rq.tag, randomUuid)
            }
            readTag(rq.tag.id)
        }

    override suspend fun deleteTag(rq: DbTagIdRequest): DbTagResponse = updateTag(rq.id, rq.lock) {
        TagTable.deleteWhere { TagTable.id eq rq.id.asString() }
        DbTagResponse.success(it)
    }

    override suspend fun searchTag(rq: DbTagFilterRequest): DbTagsResponse =
        transactionWrapper({
            val res = TagTable.select {
                buildList {
                    add(Op.TRUE)
                    if (rq.searchString.isNotBlank()) {
                        add(
                            (TagTable.name like "%${rq.searchString}%")
                                    or (TagTable.code like "%${rq.searchString}%")
                        )
                    }
                }.reduce { a, b -> a and b }
            }
            DbTagsResponse(data = res.map { TagTable.from(it) }, isSuccess = true)
        }, {
            DbTagsResponse.error(it.asAppError())
        })


    override suspend fun searchTags(rq: DbLnkFilterRequest): DbTagsResponse =
        transactionWrapper({
            val res = (TagTable innerJoin LnkTable).select(LnkTable.obj eq rq.obj)
            DbTagsResponse(data = res.map { TagTable.from(it) }, isSuccess = true)
        }, {
            DbTagsResponse.error(it.asAppError())
        })

    override suspend fun searchObjs(rq: DbLnkFilterRequest): DbObjsResponse =
        transactionWrapper({
            val res = (ObjTable innerJoin LnkTable).select(LnkTable.tag eq rq.tag)
            DbObjsResponse(data = res.map { ObjTable.from(it) }, isSuccess = true)
        }, {
            DbObjsResponse.error(it.asAppError())
        })

    override suspend fun setTags(rq: DbObjSetTagsRequest): DbTagsResponse =
        transactionWrapper({
            LnkTable.deleteWhere { LnkTable.obj eq rq.id.asString() }

                rq.tags.forEach { t ->
                    LnkTable.insert {
                        it[id] = randomUuid()
                        it[obj] = rq.id.asString()
                        it[tag] = t.id.asString()
                    }
                }

            val res = (TagTable innerJoin LnkTable).select(LnkTable.obj eq rq.id.asString())
            DbTagsResponse(data = res.map { TagTable.from(it) }, isSuccess = true)
        }, {
            DbTagsResponse.error(it.asAppError())
        })


    private fun saveLnk(o: String, t: String) {
        LnkTable.insert {
            it[id] = randomUuid()
            it[obj] = o
            it[tag] = t
        }
    }

}
