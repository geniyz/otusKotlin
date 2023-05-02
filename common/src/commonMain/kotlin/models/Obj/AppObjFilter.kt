package site.geniyz.otus.common.models

data class AppObjFilter(
    var searchString: String = "",
    var ownerId: AppUserId = AppUserId.NONE,
)
