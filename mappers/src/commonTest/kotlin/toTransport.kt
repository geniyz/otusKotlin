package site.geniyz.otus.mappers.v1.toTrans

import site.geniyz.otus.api.v1.models.*
import site.geniyz.otus.common.AppContext
import site.geniyz.otus.common.models.*

import kotlin.test.Test
import kotlin.test.assertEquals

class ToTransportTest {

    @Test
    fun objCreateTest() {
        val context = AppContext(
            requestId = AppRequestId("1234"),
            command = AppCommand.OBJ_CREATE,
            objResponse = AppObj(
                name = "obj name",
                content = "test content",
                objType = AppObjType.HREF,
            ),
            errors = mutableListOf(
                AppError(
                    code = "err",
                    group = "request",
                    field = "type",
                    message = "wrong type",
                )
            ),
            state = AppState.RUNNING,
        )

        val req = context.toTransport() as ObjCreateResponse

        assertEquals(context.requestId.asString(), req.requestId)
        assertEquals(context.objResponse.name, req.obj?.name)
        assertEquals(context.objResponse.content, req.obj?.content)
        assertEquals(ObjType.HREF, req.obj?.objType)

        assertEquals(1, req.errors?.size)
        assertEquals("err", req.errors?.firstOrNull()?.code)
        assertEquals("request", req.errors?.firstOrNull()?.group)
        assertEquals("type", req.errors?.firstOrNull()?.field)
        assertEquals("wrong type", req.errors?.firstOrNull()?.message)
    }

    @Test
    fun objListTagsTest() {
        val context = AppContext(
            requestId = AppRequestId("1234"),
            command = AppCommand.OBJ_LIST_TAGS,
            objResponse = AppObj(
                name = "obj name",
                content = "test content",
                objType = AppObjType.HREF,
            ),
            tagsResponse = mutableListOf(AppTag(
                name = "test tag",
                code = "test_tag",
            )),
            state = AppState.RUNNING,
        )

        val req = context.toTransport() as ObjListTagsResponse

        assertEquals(0, req.errors?.size ?: 0)

        assertEquals(context.requestId.asString(), req.requestId)
        assertEquals(context.objResponse.name, req.obj?.name)
        assertEquals(context.objResponse.content, req.obj?.content)
        assertEquals(ObjType.HREF, req.obj?.objType)

        assertEquals(1, req.tags?.size)
        assertEquals("test tag", req.tags?.first()?.name)
    }
}
