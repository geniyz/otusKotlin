package site.geniyz.otus.common.models

import kotlinx.datetime.Instant
import site.geniyz.otus.common.NONE

data class AppObj(
    var id:        AppObjId = AppObjId.NONE,
    var name:      String = "",
    var content:   String = "",
    var objType:   AppObjType = AppObjType.NONE,
    var authorId:  AppUserId = AppUserId.NONE,
    var createdAt: Instant = Instant.NONE,
    var updatedAt: Instant = Instant.NONE,
)
