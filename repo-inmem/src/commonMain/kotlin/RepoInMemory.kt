package site.geniyz.otus.backend.repo.inmemory

import com.benasher44.uuid.uuid4
import io.github.reactivecircus.cache4k.Cache
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import site.geniyz.otus.backend.repo.inmemory.model.*
import site.geniyz.otus.common.helpers.errorRepoConcurrency
import site.geniyz.otus.common.helpers.errorRepoUnique
import site.geniyz.otus.common.models.*
import site.geniyz.otus.common.repo.*
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

class RepoInMemory(
    initObjs:  List<AppObj> = emptyList(),
    initTags:  List<AppTag> = emptyList(),
    initLinks: Map<String, List<String>> = emptyMap(),
    ttl: Duration = 2.minutes,
    val randomUuid: () -> String = { uuid4().toString() },
) : IRepository {

    private val cacheObjs = Cache.Builder<String, ObjEntity>()
        .expireAfterWrite(ttl)
        .build()

    private val cacheTags = Cache.Builder<String, TagEntity>()
        .expireAfterWrite(ttl)
        .build()

    private val cacheLinksObj = Cache.Builder<String, List<String>>()
        .expireAfterWrite(ttl)
        .build()

    private val cacheLinksTag = Cache.Builder<String, List<String>>()
        .expireAfterWrite(ttl)
        .build()

    private val mutex: Mutex = Mutex()

    init {
        initObjs.forEach { saveObj(it) }
        initTags.forEach { saveTag(it) }
        initLinks.forEach { l ->
            l.value.forEach { saveLnk(l.key, it) }
        }
    }

    private fun saveObj(o: AppObj) {
        val entity = ObjEntity(o)
        if (entity.id == null) {
            return
        }
        cacheObjs.put(entity.id, entity)
    }

    override suspend fun createObj(rq: DbObjRequest): DbObjResponse {
        val key = randomUuid()
        val o = rq.obj.copy(id = AppObjId(key), lock = AppLock(randomUuid()))
        val entity = ObjEntity(o)


        if(cacheObjs.asMap().asSequence()
                .filter { entry ->
                    // key != entry.key &&
                            rq.obj.authorId.asString() == entry.value.author &&
                            rq.obj.name == entry.value.name
                }.any()
        ){
            return DbObjResponse(
                data = o,
                isSuccess = false,
                errors = listOf(errorRepoUnique(listOf("name", "author")))
            )
        }

        cacheObjs.put(key, entity)
        return DbObjResponse(
            data = o,
            isSuccess = true,
        )
    }

    override suspend fun readObj(rq: DbObjIdRequest): DbObjResponse {
        val key = rq.id.takeIf { it != AppObjId.NONE }?.asString() ?: return rezErrorEmptyObjId
        return cacheObjs.get(key)
            ?.let {
                DbObjResponse(
                    data = it.toInternal(),
                    isSuccess = true,
                )
            } ?: rezErrorObjNotFound
    }

    override suspend fun updateObj(rq: DbObjRequest): DbObjResponse {
        val key     = rq.obj.id.takeIf { it != AppObjId.NONE }?.asString() ?: return rezErrorEmptyObjId
        val oldLock = rq.obj.lock.takeIf { it != AppLock.NONE }?.asString() ?: return rezErrorEmptyObjLock
        val newObj  = rq.obj.copy(lock = AppLock(randomUuid()))
        val entity  = ObjEntity(newObj)
        return mutex.withLock {
            val oldObj = cacheObjs.get(key)


                when {
                    oldObj == null -> rezErrorObjNotFound
                    oldObj.lock != oldLock -> DbObjResponse(
                        data = oldObj.toInternal(),
                        isSuccess = false,
                        errors = listOf(errorRepoConcurrency(AppLock(oldLock), oldObj.lock?.let { AppLock(it) }))
                    )

                    else -> {

                        if(cacheObjs.asMap().asSequence()
                                .filter { entry ->
                                    key != entry.key &&
                                        rq.obj.authorId.asString() == entry.value.author &&
                                        rq.obj.name == entry.value.name
                                }.any()
                        ){
                            DbObjResponse(
                                data = oldObj.toInternal(),
                                isSuccess = false,
                                errors = listOf(errorRepoUnique(listOf("name", "author")))
                            )
                        }

                        cacheObjs.put(key, entity)
                        DbObjResponse(
                            data = newObj,
                            isSuccess = true,
                        )
                    }

            }
        }
    }

    override suspend fun deleteObj(rq: DbObjIdRequest): DbObjResponse {
        val key = rq.id.takeIf { it != AppObjId.NONE }?.asString() ?: return rezErrorEmptyObjId
        val oldLock = rq.lock.takeIf { it != AppLock.NONE }?.asString() ?: return rezErrorEmptyObjLock
        return mutex.withLock {
            val oldObj = cacheObjs.get(key)
            when {
                oldObj == null -> rezErrorObjNotFound
                oldObj.lock != oldLock -> DbObjResponse(
                    data = oldObj.toInternal(),
                    isSuccess = false,
                    errors = listOf(errorRepoConcurrency(AppLock(oldLock), oldObj.lock?.let { AppLock(it) }))
                )

                else -> {
                    cacheObjs.invalidate(key)
                    DbObjResponse(
                        data = oldObj.toInternal(),
                        isSuccess = true,
                    )
                }
            }
        }
    }

    override suspend fun searchObj(rq: DbObjFilterRequest): DbObjsResponse {
        val result = cacheObjs.asMap().asSequence()
            .filter { entry ->
                rq.ownerId.takeIf { it != AppUserId.NONE }?.let {
                    it.asString() == entry.value.author
                } ?: true
            }
            .filter { entry ->
                rq.searchString.takeIf { it.isNotBlank() }?.let {
                    (entry.value.name?.contains(it) ?: false) || (entry.value.content?.contains(it) ?: false)
                } ?: true
            }
            .map { it.value.toInternal() }
            .toList()
        return DbObjsResponse(
            data = result,
            isSuccess = true
        )
    }

    // TAG:
    private fun saveTag(o: AppTag) {
        val entity = TagEntity(o)
        if (entity.id == null) {
            return
        }
        cacheTags.put(entity.id, entity)
    }

    override suspend fun createTag(rq: DbTagRequest): DbTagResponse {
        val key = randomUuid()
        val tg = rq.tag.copy(id = AppTagId(key), lock = AppLock(randomUuid()))
        val entity = TagEntity(tg)
        cacheTags.put(key, entity)
        return DbTagResponse(
            data = tg,
            isSuccess = true,
        )
    }

    override suspend fun readTag(rq: DbTagIdRequest): DbTagResponse {
        val key = rq.id.takeIf { it != AppTagId.NONE }?.asString() ?: return rezErrorEmptyTagId
        return cacheTags.get(key)
            ?.let {
                DbTagResponse(
                    data = it.toInternal(),
                    isSuccess = true,
                )
            } ?: rezErrorTagNotFound
    }

    override suspend fun updateTag(rq: DbTagRequest): DbTagResponse {
        val key     = rq.tag.id.takeIf { it != AppTagId.NONE }?.asString() ?: return rezErrorEmptyTagId
        val oldLock = rq.tag.lock.takeIf { it != AppLock.NONE }?.asString() ?: return rezErrorEmptyTagLock
        val newObj  = rq.tag.copy(lock = AppLock(randomUuid()))
        val entity  = TagEntity(newObj)
        return mutex.withLock {
            val oldObj = cacheTags.get(key)
            when {
                oldObj == null              -> rezErrorTagNotFound
                oldObj.lock != oldLock      -> DbTagResponse(
                    data = oldObj.toInternal(),
                    isSuccess = false,
                    errors = listOf(errorRepoConcurrency(AppLock(oldLock), oldObj.lock?.let { AppLock(it) }))
                )

                else                        -> {
                    cacheTags.put(key, entity)
                    DbTagResponse(
                        data = newObj,
                        isSuccess = true,
                    )
                }
            }
        }
    }

    override suspend fun deleteTag(rq: DbTagIdRequest): DbTagResponse {
        val key = rq.id.takeIf { it != AppTagId.NONE }?.asString() ?: return rezErrorEmptyTagId
        val oldLock = rq.lock.takeIf { it != AppLock.NONE }?.asString() ?: return rezErrorEmptyTagLock
        return mutex.withLock {
            val oldObj = cacheTags.get(key)
            when {
                oldObj == null -> rezErrorTagNotFound
                oldObj.lock != oldLock -> DbTagResponse(
                    data = oldObj.toInternal(),
                    isSuccess = false,
                    errors = listOf(errorRepoConcurrency(AppLock(oldLock), oldObj.lock?.let { AppLock(it) }))
                )

                else -> {
                    cacheTags.invalidate(key)
                    DbTagResponse(
                        data = oldObj.toInternal(),
                        isSuccess = true,
                    )
                }
            }
        }
    }

    override suspend fun searchTag(rq: DbTagFilterRequest): DbTagsResponse {
        val result = cacheTags.asMap().asSequence()
            .filter { entry ->
                rq.searchString.takeIf { it.isNotBlank() }?.let {
                    (entry.value.name?.contains(it) ?: false) || (entry.value.code?.contains(it) ?: false)
                } ?: true
            }
            .map { it.value.toInternal() }
            .toList()
        return DbTagsResponse(
            data = result,
            isSuccess = true
        )
    }

    /*
    override suspend fun createLnk(rq: DbLnkRequest): DbLnkResponse {
        val tags = (cacheLinksObj.get(rq.obj.id.asString()) ?: emptyList()).toMutableList()
        tags.add(rq.tag.id.asString())
        cacheLinksObj.put(rq.obj.id.asString(), tags)

        val objs = (cacheLinksTag.get(rq.tag.id.asString()) ?: emptyList()).toMutableList()
        objs.add(rq.obj.id.asString())
        cacheLinksTag.put(rq.tag.id.asString(), objs)

        return DbLnkResponse(
            data = rq.obj to rq.tag,
            isSuccess = true,
        )
    }

    override suspend fun deleteLnk(rq: DbLnkRequest): DbLnkResponse {
        return mutex.withLock {
            cacheLinksObj.invalidate(rq.obj.id.asString())
            cacheLinksTag.invalidate(rq.tag.id.asString())
            DbLnkResponse(
                data = rq.obj to rq.tag,
                isSuccess = true,
                )
        }
    }

    override suspend fun searchLnk(rq: DbLnkFilterRequest): DbLnksResponse {
        val result = when {
            rq.obj.isNotBlank() -> cacheLinksObj.get(rq.obj)?.map{
                AppObjId(rq.obj) to AppTagId(it)
            }

            rq.tag.isNotBlank() -> cacheLinksTag.get(rq.tag)?.map{
                AppObjId(it) to AppTagId(rq.tag)
            }

            else -> emptyList()
            }
        return DbLnksResponse(
            data = result,
            isSuccess = true
        )
    }
    */


    private fun saveLnk(o: String, t: String) {
        val tags = (cacheLinksObj.get(o) ?: emptyList()).toMutableList()
        tags.add(t)
        cacheLinksObj.put(o, tags)

        val objs = (cacheLinksTag.get(t) ?: emptyList()).toMutableList()
        objs.add(o)
        cacheLinksTag.put(t, objs)
    }

    override suspend fun searchTags(rq: DbLnkFilterRequest): DbTagsResponse {
        val result = cacheLinksObj.get(rq.obj)?.mapNotNull {
            cacheTags.get(it)?.toInternal()
        }
        return DbTagsResponse(
            data = result,
            isSuccess = true
        )
    }

    override suspend fun searchObjs(rq: DbLnkFilterRequest): DbObjsResponse {
        val result = cacheLinksTag.get(rq.tag)?.mapNotNull {
            cacheObjs.get(it)?.toInternal()
        }
        return DbObjsResponse(
            data = result,
            isSuccess = true
        )
    }

    override suspend fun setTags(rq: DbObjSetTagsRequest): DbTagsResponse {
        val oid = rq.id.asString()
        val tags = (cacheLinksObj.get(oid) ?: emptyList()).toMutableList()
        rq.tags.forEach {
            tags.add(it.id.asString())
        }
        val unqtags = tags.filterIndexed { i, t -> i == tags.indexOf(t) }
        cacheLinksObj.put(oid, unqtags)

        unqtags.forEach {
            cacheLinksTag.put(
                it,
                (cacheLinksTag.get(it) ?: emptyList()).toMutableList().apply{
                    add(oid)
                }
            )
        }

        return DbTagsResponse(
            data = unqtags.map { cacheTags.get(it)?.toInternal() }.filterNotNull(),
            isSuccess = true,
        )
    }

    companion object {
        val rezErrorEmptyObjId = DbObjResponse(
            data = null,
            isSuccess = false,
            errors = listOf(
                AppError(
                    code = "id-empty",
                    group = "validation",
                    field = "id",
                    message = "Id must not be null or blank"
                )
            )
        )
        val rezErrorEmptyObjLock = DbObjResponse(
            data = null,
            isSuccess = false,
            errors = listOf(
                AppError(
                    code = "lock-empty",
                    group = "validation",
                    field = "lock",
                    message = "Lock must not be null or blank"
                )
            )
        )
        val rezErrorObjNotFound = DbObjResponse(
            isSuccess = false,
            data = null,
            errors = listOf(
                AppError(
                    code = "not-found",
                    field = "id",
                    message = "Obj not Found"
                )
            )
        )

        val rezErrorEmptyTagId = DbTagResponse(
            data = null,
            isSuccess = false,
            errors = listOf(
                AppError(
                    code = "id-empty",
                    group = "validation",
                    field = "id",
                    message = "Id must not be null or blank"
                )
            )
        )
        val rezErrorEmptyTagLock = DbTagResponse(
            data = null,
            isSuccess = false,
            errors = listOf(
                AppError(
                    code = "lock-empty",
                    group = "validation",
                    field = "lock",
                    message = "Lock must not be null or blank"
                )
            )
        )
        val rezErrorTagNotFound = DbTagResponse(
            isSuccess = false,
            data = null,
            errors = listOf(
                AppError(
                    code = "not-found",
                    field = "id",
                    message = "Tag not Found"
                )
            )
        )

    }
}
