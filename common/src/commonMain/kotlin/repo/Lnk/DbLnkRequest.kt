package site.geniyz.otus.common.repo

import site.geniyz.otus.common.models.*

data class DbLnkRequest(
    val tag: AppTag = AppTag(),
    val obj: AppObj = AppObj(),
)
