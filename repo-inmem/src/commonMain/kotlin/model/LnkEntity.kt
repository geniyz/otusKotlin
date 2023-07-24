package site.geniyz.otus.backend.repository.inmemory.model

import site.geniyz.otus.common.models.*

data class LnkEntity(
    val objId:       String? = null,
    val tagId:       String? = null,
) {
    constructor(obj: AppObj, tag: AppTag): this(obj.id.asString(), tag.id.asString())
}
