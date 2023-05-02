package site.geniyz.otus.common.models

data class AppTag(
    var id: AppTagId = AppTagId.NONE,
    var name: String = "",
    var code: String = "",
)
