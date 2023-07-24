package site.geniyz.otus.backend.repository.inmemory.model

import kotlinx.datetime.Instant
import site.geniyz.otus.common.NONE
import site.geniyz.otus.common.helpers.asInstant
import site.geniyz.otus.common.helpers.asString
import site.geniyz.otus.common.models.*

data class ObjEntity(
    val id:        String? = null,
    val name:      String? = null,
    val content:   String? = null,
    val objType:   String? = null,
    val author:    String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null,
    val lock:      String? = null,
) {
    constructor(model: AppObj): this(
        id = model.id.asString().takeIf { it.isNotBlank() },
        name = model.name.takeIf { it.isNotBlank() },
        content = model.content.takeIf { it.isNotBlank() },
        author = model.authorId.asString().takeIf { it.isNotBlank() },
        objType = model.objType.takeIf { it != AppObjType.NONE }?.name,
        createdAt = model.createdAt.asString(),
        updatedAt = model.updatedAt.asString(),
        lock = model.lock.asString().takeIf { it.isNotBlank() }
    )

    fun toInternal() = AppObj(
        id = id?.let { AppObjId(it) }?: AppObjId.NONE,
        name = name?: "",
        content = content ?: "",
        authorId = author?.let { AppUserId(it) }?: AppUserId.NONE,
        objType = objType?.let { AppObjType.valueOf(it) }?: AppObjType.NONE,
        createdAt = createdAt?.asInstant() ?: Instant.NONE,
        updatedAt = updatedAt?.asInstant() ?: Instant.NONE,
        lock = lock?.let { AppLock(it) } ?: AppLock.NONE,
    )
}
