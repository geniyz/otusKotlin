package site.geniyz.otus.app.stubs

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import site.geniyz.otus.api.v1.apiV1Mapper
import site.geniyz.otus.api.v1.models.*
import site.geniyz.otus.app.auth.AuthConfig
import site.geniyz.otus.app.auth.addAuth
import site.geniyz.otus.app.helpers.testSettings
import site.geniyz.otus.app.module
import kotlin.test.Test
import kotlin.test.assertEquals

class V1ObjStubApiTest {

    @Test
    fun create() = testApplication {
        application { module(testSettings()) }
        val response = client.post("/v1/obj/create") {
            val requestObj = ObjCreateRequest(
                requestId = "12345",
                obj = ObjCreateObject(
                    name = "Объектъ",
                    content = "Объект-текст",
                    objType = ObjType.TEXT,
                ),
                debug = ObjDebug(
                    mode = RequestDebugMode.STUB,
                    stub = ObjRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            addAuth(config = AuthConfig.TEST)
            val requestJson = apiV1Mapper.encodeToString(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        val responseObj = apiV1Mapper.decodeFromString<ObjCreateResponse>(responseJson)
        assertEquals(200, response.status.value)
        assertEquals("obj-text-0000", responseObj.obj?.id)
    }

    @Test
    fun read() = testApplication {
        application { module(testSettings()) }
        val response = client.post("/v1/obj/read") {
            val requestObj = ObjReadRequest(
                requestId = "12345",
                obj = ObjReadObject("obj-text-0000"),
                debug = ObjDebug(
                    mode = RequestDebugMode.STUB,
                    stub = ObjRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            addAuth(config = AuthConfig.TEST)
            val requestJson = apiV1Mapper.encodeToString(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        val responseObj = apiV1Mapper.decodeFromString<ObjReadResponse>(responseJson)
        assertEquals(200, response.status.value)
        assertEquals("obj-text-0000", responseObj.obj?.id)
    }

    @Test
    fun update() = testApplication {
        application { module(testSettings()) }
        val response = client.post("/v1/obj/update") {
            val requestObj = ObjUpdateRequest(
                requestId = "12345",
                obj = ObjUpdateObject(
                    id = "obj-text-0000",
                    name = "Объектъ",
                    content = "Объект-текст",
                    objType = ObjType.TEXT,
                ),
                debug = ObjDebug(
                    mode = RequestDebugMode.STUB,
                    stub = ObjRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            addAuth(config = AuthConfig.TEST)
            val requestJson = apiV1Mapper.encodeToString(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        val responseObj = apiV1Mapper.decodeFromString<ObjUpdateResponse>(responseJson)
        assertEquals(200, response.status.value)
        assertEquals("obj-text-0000", responseObj.obj?.id)
    }

    @Test
    fun delete() = testApplication {
        application { module(testSettings()) }
        val response = client.post("/v1/obj/delete") {
            val requestObj = ObjDeleteRequest(
                requestId = "12345",
                obj = ObjDeleteObject(
                    id = "obj-text-0000",
                ),
                debug = ObjDebug(
                    mode = RequestDebugMode.STUB,
                    stub = ObjRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            addAuth(config = AuthConfig.TEST)
            val requestJson = apiV1Mapper.encodeToString(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        val responseObj = apiV1Mapper.decodeFromString<ObjDeleteResponse>(responseJson)
        assertEquals(200, response.status.value)
        assertEquals("obj-text-0000", responseObj.obj?.id)
    }

    @Test
    fun search() = testApplication {
        application { module(testSettings()) }
        val response = client.post("/v1/obj/search") {
            val requestObj = ObjSearchRequest(
                requestId = "12345",
                objFilter = ObjSearchFilter(),
                debug = ObjDebug(
                    mode = RequestDebugMode.STUB,
                    stub = ObjRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            addAuth(config = AuthConfig.TEST)
            val requestJson = apiV1Mapper.encodeToString(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        val responseObj = apiV1Mapper.decodeFromString<ObjSearchResponse>(responseJson)
        assertEquals(200, response.status.value)
        assertEquals("obj-text-0001", responseObj.objs?.first()?.id)
    }

    @Test
    fun objListTags() = testApplication {
        application { module(testSettings()) }
        val response = client.post("/v1/obj/listTags") {
            val requestObj = ObjListTagsRequest(
                requestId = "12345",
                obj = ObjListTagsObject(
                    id = "666"
                ),
                debug = ObjDebug(
                    mode = RequestDebugMode.STUB,
                    stub = ObjRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            addAuth(config = AuthConfig.TEST)
            val requestJson = apiV1Mapper.encodeToString(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        val responseObj = apiV1Mapper.decodeFromString<ObjListTagsResponse>(responseJson)
        assertEquals(200, response.status.value)
        assertEquals("tag-0001", responseObj.tags?.first()?.id)
    }

    @Test
    fun objSetTags() = testApplication {
        application { module(testSettings()) }
        val response = client.post("/v1/obj/setTags") {
            val requestObj = ObjSetTagsRequest(
                requestId = "12345",
                obj = ObjSetTagsObject(
                    id = "666"
                ),
                debug = ObjDebug(
                    mode = RequestDebugMode.STUB,
                    stub = ObjRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            addAuth(config = AuthConfig.TEST)
            val requestJson = apiV1Mapper.encodeToString(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        val responseObj = apiV1Mapper.decodeFromString<ObjSetTagsResponse>(responseJson)
        assertEquals(200, response.status.value)
        assertEquals("tag-0001", responseObj.tags?.first()?.id)
    }
}
