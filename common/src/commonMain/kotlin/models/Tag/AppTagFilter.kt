package site.geniyz.otus.common.models

data class AppTagFilter(
    var searchString: String = "",
    var ownerId: AppUserId = AppUserId.NONE,
)
