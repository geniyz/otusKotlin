package site.geniyz.otus.backend.repo.inmemory.model

import kotlinx.datetime.Instant
import site.geniyz.otus.common.NONE
import site.geniyz.otus.common.helpers.asInstant
import site.geniyz.otus.common.helpers.asString
import site.geniyz.otus.common.models.*

data class TagEntity(
    val id:        String? = null,
    val name:      String? = null,
    val code:      String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null,
    val lock:      String? = null,
) {
    constructor(model: AppTag): this(
        id = model.id.asString().takeIf { it.isNotBlank() },
        name = model.name.takeIf { it.isNotBlank() },
        code = model.code.takeIf { it.isNotBlank() },
        createdAt = model.createdAt.asString(),
        updatedAt = model.updatedAt.asString(),
        lock = model.lock.asString().takeIf { it.isNotBlank() }
    )

    fun toInternal() = AppTag(
        id = id?.let { AppTagId(it) }?: AppTagId.NONE,
        name = name?: "",
        code = code ?: "",
        createdAt = createdAt?.asInstant() ?: Instant.NONE,
        updatedAt = updatedAt?.asInstant() ?: Instant.NONE,
        lock = lock?.let { AppLock(it) } ?: AppLock.NONE,
    )
}
