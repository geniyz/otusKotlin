package site.geniyz.otus.mappers.v1.fromTrans

import site.geniyz.otus.api.v1.models.*
import site.geniyz.otus.common.AppContext
import site.geniyz.otus.common.models.*
import site.geniyz.otus.common.stubs.AppStubs

import kotlin.test.Test
import kotlin.test.assertEquals

class FromTransportTest {
    @Test
    fun objCreateTest() {
        val req = ObjCreateRequest(
            requestId = "1234",
            debug = ObjDebug(
                mode = RequestDebugMode.STUB,
                stub = ObjRequestDebugStubs.SUCCESS,
            ),
            obj = ObjCreateObject(
                name = "obj name",
                content = "test content",
                objType = ObjType.TEXT,
            ),
        )

        val context = AppContext()
        context.fromTransport(req)

        assertEquals(AppStubs.SUCCESS, context.stubCase)
        assertEquals(AppWorkMode.STUB, context.workMode)
        assertEquals(req.obj?.name, context.objRequest.name)
        assertEquals(req.obj?.content, context.objRequest.content)
        assertEquals(AppObjType.TEXT, context.objRequest.objType)
    }

    @Test
    fun objReadTest() {
        val req = ObjReadRequest(
            requestId = "1234",
            debug = ObjDebug(
                mode = RequestDebugMode.STUB,
                stub = ObjRequestDebugStubs.SUCCESS,
            ),
            obj = ObjReadObject(
                id = "IDOBJ"
            ),
        )

        val context = AppContext()
        context.fromTransport(req)

        assertEquals(AppStubs.SUCCESS, context.stubCase)
        assertEquals(AppWorkMode.STUB, context.workMode)
        assertEquals(req.obj?.id, context.objRequest.id.asString())
    }


    @Test
    fun objSetTagsTest() {
        val req = ObjSetTagsRequest(
            requestId = "9012",
            debug = ObjDebug(
                mode = RequestDebugMode.STUB,
                stub = ObjRequestDebugStubs.SUCCESS,
            ),
            obj = ObjSetTagsObject(
                id = "IDOBJ",
                tags = listOf("tag1","tag2","tag3")
            ),
        )

        val context = AppContext()
        context.fromTransport(req)

        assertEquals(AppStubs.SUCCESS, context.stubCase)
        assertEquals(AppWorkMode.STUB, context.workMode)
        assertEquals(req.obj?.id, context.objRequest.id.asString())
        assertEquals(req.obj?.tags?.size ?: 0, context.tagsRequest.size)

        assertEquals(req.obj?.tags?.sorted()?.joinToString(","), context.tagsRequest.map{ it.code }.sorted().joinToString(","))

    }

}
