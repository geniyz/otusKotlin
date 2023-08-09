package site.geniyz.otus.stubs

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import site.geniyz.otus.common.NONE
import site.geniyz.otus.common.models.*

object AppStubObjs {
    private val OBJ_TEXT1 = AppObj(
            id        = AppObjId("obj-text-0000"),
            name      = "Текст1",             // наименование
            content   = "Какой-то тест",      // содержимое
            objType   = AppObjType.TEXT,      // тип объекта
            authorId  = AppUserId("000"),  // автор-владелец
            createdAt = Clock.System.now(),   // дата-время создания
            updatedAt = Instant.NONE,         // дата-время последнего изменения
            lock      = AppLock("123-234-abc-ABC"),
            permissionsClient = mutableSetOf(
                AppObjPermissionClient.READ,
                AppObjPermissionClient.UPDATE,
                AppObjPermissionClient.SET_TAGS,
                AppObjPermissionClient.DELETE,
            ),
        )
    private val OBJ_HREF1 = OBJ_TEXT1.copy(
        id        = AppObjId("obj-href-0000"),
        name      = "Ссылка1",
        content   = "https://ya.ru",
        objType   = AppObjType.HREF,
    )

    fun getText()= OBJ_TEXT1
    fun getHref()= OBJ_HREF1

    fun prepareResult(block: AppObj.() -> Unit): AppObj = getText().apply(block)

    fun prepareSearchList(filter: String)= listOf(
        appObjText("obj-text-0001", filter),
        appObjText("obj-text-0002", filter),
        appObjText("obj-text-0003", filter),
        appObjText("obj-text-0004", filter),
        appObjHref("obj-href-0001", filter),
        appObjHref("obj-href-0002", filter),
        appObjHref("obj-href-0003", filter),
    )

    private fun appObjText(id: String, filter: String) = OBJ_TEXT1.copy(
        id      =  AppObjId(id),
        name    = "$filter $id",
        content = "desc $filter $id",
        objType = AppObjType.TEXT,
    )
    private fun appObjHref(id: String, filter: String) = OBJ_HREF1.copy(
        id      =  AppObjId(id),
        name    = "$filter $id",
        content = "http://www.$id.org",
        objType = AppObjType.HREF,
    )

}
