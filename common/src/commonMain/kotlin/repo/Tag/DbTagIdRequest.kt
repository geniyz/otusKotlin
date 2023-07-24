package site.geniyz.otus.common.repo

import site.geniyz.otus.common.models.*

data class DbTagIdRequest(
    val id: AppTagId,
    val lock: AppLock = AppLock.NONE
) {
    constructor(tag: AppTag): this(id = tag.id, lock = tag.lock)
}
