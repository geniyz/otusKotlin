package site.geniyz.otus.app.repo

import io.ktor.client.*
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

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class V1ObjInmemoryApiTest {
    private val createObj = ObjCreateObject(
        name = "Объектъ",
        content = "Объект-текст",
        objType = ObjType.TEXT,

    )
    private val requestObj = ObjCreateRequest(
        requestId = "12345",
        obj = createObj,
        debug = ObjDebug(
            mode = RequestDebugMode.TEST,
            // stub = ObjRequestDebugStubs.SUCCESS
        )
    )
    @Test
    fun create() = testApplication {
        val responseObj = initObject(client)
        assertEquals(createObj.name, responseObj.obj?.name)
        assertEquals(createObj.content, responseObj.obj?.content)
        assertEquals(createObj.objType, responseObj.obj?.objType)
    }

    @Test
    fun read() = testApplication {
        val objCreateResponse = initObject(client)
        val oldId = objCreateResponse.obj?.id
        val response = client.post("/v1/obj/read") {
            val requestObj = ObjReadRequest(
                requestId = "12345",
                obj = ObjReadObject(oldId),
                debug = ObjDebug(
                    mode = RequestDebugMode.TEST,
                )
            )
            contentType(ContentType.Application.Json)
            addAuth(id = "123", config = AuthConfig.TEST)
            val requestJson = apiV1Mapper.encodeToString(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        val responseObj = apiV1Mapper.decodeFromString<ObjReadResponse>(responseJson)
        assertEquals(200, response.status.value)
        assertEquals(oldId, responseObj.obj?.id)
    }

    @Test
    fun update() = testApplication {
        val initObject = initObject(client)
        val objUpdate = ObjUpdateObject(
            id = initObject.obj?.id,
            name = "Объект",
            content = "Новое описание",
            objType = ObjType.TEXT,
            lock = initObject.obj?.lock,
        )

        val response = client.post("/v1/obj/update") {
            val requestObj = ObjUpdateRequest(
                requestId = "12345",
                obj = objUpdate,
                debug = ObjDebug(
                    mode = RequestDebugMode.TEST,
                )
            )
            contentType(ContentType.Application.Json)
            addAuth(id = "123", config = AuthConfig.TEST)
            val requestJson = apiV1Mapper.encodeToString(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        val responseObj = apiV1Mapper.decodeFromString<ObjUpdateResponse>(responseJson)
        assertEquals(200, response.status.value)
        assertEquals(objUpdate.id, responseObj.obj?.id)
        assertEquals(objUpdate.name, responseObj.obj?.name)
        assertEquals(objUpdate.content, responseObj.obj?.content)
    }

    @Test
    fun delete() = testApplication {
        val initObject = initObject(client)
        val id = initObject.obj?.id
        val lock = initObject.obj?.lock
        val response = client.post("/v1/obj/delete") {
            val requestObj = ObjDeleteRequest(
                requestId = "12345",
                obj = ObjDeleteObject(id, lock),
                debug = ObjDebug(
                    mode = RequestDebugMode.TEST,
                )
            )
            contentType(ContentType.Application.Json)
            addAuth(id = "123", config = AuthConfig.TEST)
            val requestJson = apiV1Mapper.encodeToString(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        val responseObj = apiV1Mapper.decodeFromString<ObjDeleteResponse>(responseJson)
        assertEquals(200, response.status.value)
        assertEquals(id, responseObj.obj?.id)
    }

    @Test
    fun search() = testApplication {
        val initObject = initObject(client)
        val response = client.post("/v1/obj/search") {
            val requestObj = ObjSearchRequest(
                requestId = "12345",
                objFilter = ObjSearchFilter(),
                debug = ObjDebug(
                    mode = RequestDebugMode.TEST,
                )
            )
            contentType(ContentType.Application.Json)
            addAuth(id = "123", config = AuthConfig.TEST)
            val requestJson = apiV1Mapper.encodeToString(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        val responseObj = apiV1Mapper.decodeFromString<ObjSearchResponse>(responseJson)
        assertEquals(200, response.status.value)
        assertNotEquals(0, responseObj.objs?.size)
        assertEquals(initObject.obj?.id, responseObj.objs?.first()?.id)
    }

    @Test
    fun setTags() = testApplication {
        val initObject = initObject(client)
        val objId = initObject.obj?.id
        val objLock = initObject.obj?.lock
        val newTags = listOf("метка", "тэг", "тег")
        val response = client.post("/v1/obj/setTags") {
            val requestObj = ObjSetTagsRequest(
                requestId = "12345",
                obj = ObjSetTagsObject(
                    id = objId,
                    tags = newTags,
                    lock = objLock,
                ),
                debug = ObjDebug(
                    mode = RequestDebugMode.TEST,
                )
            )
            contentType(ContentType.Application.Json)
            addAuth(id = "123", config = AuthConfig.TEST)
            val requestJson = apiV1Mapper.encodeToString(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        val responseObj = apiV1Mapper.decodeFromString<ObjSetTagsResponse>(responseJson)
        assertEquals(200, response.status.value)
        assertEquals(objId, responseObj.obj?.id)
        assertEquals(newTags.size, responseObj.tags?.size)
    }

    @Test
    fun listTags() = testApplication {
        val objId = initObject(client).obj?.id
        val response = client.post("/v1/obj/listTags") {
            val requestObj = ObjListTagsRequest(
                requestId = "12345",
                obj = ObjListTagsObject(
                    id = objId,
                ),
                debug = ObjDebug(
                    mode = RequestDebugMode.TEST,
                )
            )
            contentType(ContentType.Application.Json)
            addAuth(id = "123", config = AuthConfig.TEST)
            val requestJson = apiV1Mapper.encodeToString(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        val responseObj = apiV1Mapper.decodeFromString<ObjListTagsResponse>(responseJson)
        assertEquals(200, response.status.value)
        assertEquals(objId, responseObj.obj?.id)
        assertEquals(3, responseObj.tags?.size)
    }


    private suspend fun initObject(client: HttpClient): ObjCreateResponse {
        val response = client.post("/v1/obj/create") {
            contentType(ContentType.Application.Json)
            val requestJson = apiV1Mapper.encodeToString(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        assertEquals(200, response.status.value)
        return apiV1Mapper.decodeFromString<ObjCreateResponse>(responseJson)
    }
}
