package site.geniyz.otus.common.repo

import site.geniyz.otus.common.models.*

data class DbObjIdRequest(
    val id: AppObjId,
    val lock: AppLock = AppLock.NONE,
) {
    constructor(obj: AppObj): this(id = obj.id, lock = obj.lock)
}
