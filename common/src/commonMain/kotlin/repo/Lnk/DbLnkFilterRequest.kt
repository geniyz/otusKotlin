package site.geniyz.otus.common.repo

import site.geniyz.otus.common.models.*

data class DbLnkFilterRequest(
    val obj: String = "", // это идентификатор объекта
    val tag: String = "", // это идентификатори метки
    val ownerId: AppUserId = AppUserId.NONE,
)
