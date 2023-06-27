package site.geniyz.otus.stubs

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import site.geniyz.otus.common.NONE
import site.geniyz.otus.common.models.*

object AppStubTags {
    private val TAG_1: AppTag
        get() = AppTag(
            id        = AppTagId("tag-0001"),
            name      = "Метка1",
            code      = "Label1",
            createdAt = Clock.System.now(),   // дата-время создания
            updatedAt = Instant.NONE,         // дата-время последнего изменения
        )
    fun get()= TAG_1

    fun prepareResult(block: AppTag.() -> Unit): AppTag = get().apply(block)

    fun prepareSearchList(filter: String)= listOf(
        appTag("tag-0001", filter),
        appTag("tag-0002", filter),
        appTag("tag-0003", filter),
        appTag("tag-0004", filter),
        appTag("tag-0005", filter),
        appTag("tag-0006", filter),
        appTag("tag-0007", filter),
    )

    private fun appTag(id: String, filter: String) = TAG_1.copy(
        id      =  AppTagId(id),
        name    = "$filter $id",
        code    = "${filter}_${id}",
    )

}
