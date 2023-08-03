package site.geniyz.otus.backend.repo.tests

import site.geniyz.otus.common.models.*

abstract class BaseInitObjs(val op: String): IInitObjects<AppObj> {

    open val lockOld: AppLock = AppLock("20000000-0000-0000-0000-000000000001")
    open val lockBad: AppLock = AppLock("20000000-0000-0000-0000-000000000009")

    fun createInitTestModelObj(
        suf: String,
        ownerId: AppUserId = AppUserId("owner-123"),
        objType: AppObjType = AppObjType.TEXT,
        lock: AppLock = lockOld,
    ) = AppObj(
        id = AppObjId("obj-repo-$op-$suf"),
        name = "$suf stub",
        content = "$suf stub content",
        authorId = ownerId,
        objType = objType,
        lock = lock,
    )

    fun createInitTestModelTag(
        suf: String,
        lock: AppLock = lockOld,
    ) = AppTag(
        id = AppTagId("tag-repo-$op-$suf"),
        name = "$suf stub",
        lock = lock,
    )

}
