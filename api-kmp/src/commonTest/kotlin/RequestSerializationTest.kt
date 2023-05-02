package site.geniyz.otus.api.v1

import site.geniyz.otus.api.v1.models.*

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class RequestSerializationTest {
    private val request : IRequest = ObjCreateRequest(
        requestType = "objCreate",
        requestId = "requestId",
        debug = ObjDebug(
            mode = RequestDebugMode.STUB,
            stub = ObjRequestDebugStubs.BAD_NAME,
        ),
        obj = ObjCreateObject(
            name = "Test title",
            content = "Test content",
            objType = ObjType.TEXT,
            )
    )

    @Test
    fun serialize() {
        val json = apiV1Mapper.encodeToString(request)
        // println(json)

        assertContains(json, Regex("\"name\":\\s*\"Test title\""))
        assertContains(json, Regex("\"mode\":\\s*\"stub\""))
        assertContains(json, Regex("\"stub\":\\s*\"badName\""))
        assertContains(json, Regex("\"requestType\":\\s*\"objCreate\""))
    }

    @Test
    fun serializeWithoutType() {
        val json = apiV1Mapper.encodeToString((request as ObjCreateRequest).copy(requestType = null) as IRequest)
        // println(json)

        assertContains(json, Regex("\"name\":\\s*\"Test title\""))
        assertContains(json, Regex("\"mode\":\\s*\"stub\""))
        assertContains(json, Regex("\"stub\":\\s*\"badName\""))
        assertContains(json, Regex("\"requestType\":\\s*\"objCreate\""))
    }

    @Test
    fun deserialize() {
        val json = apiV1Mapper.encodeToString(request)
        val obj = apiV1Mapper.decodeFromString(json) as ObjCreateRequest

        assertEquals(request, obj)
    }
    @Test
    fun deserializeNaked() {
        val req = apiV1Mapper.decodeFromString(
            """{
             "requestType": "objCreate"
            ,"requestId":   "requestId"
            ,"debug": {
                 "mode": "stub"
                ,"stub": "badName"
                }
            ,"obj": {
                 "name":    "Test Title"
                ,"content": "Test content"
                ,"objType": "text"
                }
            }"""
        ) as IRequest

        val obj = (req as ObjCreateRequest).obj!!

        assertEquals("requestId", req.requestId)
        assertEquals("Test Title", obj.name )
        assertEquals("Test content", obj.content)
        assertEquals(ObjType.TEXT, obj.objType)
    }
}
