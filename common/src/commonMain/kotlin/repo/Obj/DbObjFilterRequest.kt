package site.geniyz.otus.common.repo

import site.geniyz.otus.common.models.*

data class DbObjFilterRequest(
    val searchString: String = "",
    val ownerId: AppUserId = AppUserId.NONE,
)
