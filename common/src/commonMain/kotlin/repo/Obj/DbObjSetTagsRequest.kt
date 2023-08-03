package site.geniyz.otus.common.repo

import site.geniyz.otus.common.models.*

data class DbObjSetTagsRequest(
    val id: AppObjId,
    val tags: List<AppTag>,
) {
    constructor(obj: AppObj, tags: List<AppTag>): this(id = obj.id, tags = tags)
}
