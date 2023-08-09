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

class V1TagStubApiTest {
    @Test
    fun delete() = testApplication {
        application { module(testSettings()) }
        val response = client.post("/v1/tag/delete") {
            val requestObj = TagDeleteRequest(
                requestId = "12345",
                tag = TagDeleteObject(
                    id = "tag-0001",
                ),
                debug = TagDebug(
                    mode = RequestDebugMode.STUB,
                    stub = TagRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            addAuth(config = AuthConfig.TEST)
            val requestJson = apiV1Mapper.encodeToString(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        val responseObj = apiV1Mapper.decodeFromString<TagDeleteResponse>(responseJson)
        assertEquals(200, response.status.value)
        assertEquals("tag-0001", responseObj.tag?.id)
    }

    @Test
    fun search() = testApplication {
        application { module(testSettings()) }
        val response = client.post("/v1/tag/search") {
            val requestObj = TagSearchRequest(
                requestId = "12345",
                tagFilter = TagSearchFilter("Метка"),
                debug = TagDebug(
                    mode = RequestDebugMode.STUB,
                    stub = TagRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            addAuth(config = AuthConfig.TEST)
            val requestJson = apiV1Mapper.encodeToString(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        val responseObj = apiV1Mapper.decodeFromString<TagSearchResponse>(responseJson)
        assertEquals(200, response.status.value)
        assertEquals("tag-0001", responseObj.tags?.first()?.id)
    }

    @Test
    fun objListTags() = testApplication {
        application { module(testSettings()) }
        val response = client.post("/v1/tag/objects") {
            val requestObj = TagListObjsRequest(
                requestId = "12345",
                tag = TagListObjsObject(
                    id = "tag-0001"
                ),
                debug = TagDebug(
                    mode = RequestDebugMode.STUB,
                    stub = TagRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            addAuth(config = AuthConfig.TEST)
            val requestJson = apiV1Mapper.encodeToString(requestObj)
            setBody(requestJson)
        }
        val responseJson = response.bodyAsText()
        val responseObj = apiV1Mapper.decodeFromString<TagListObjsResponse>(responseJson)
        assertEquals(200, response.status.value)
        assertEquals("obj-text-0001", responseObj.objs?.first()?.id)
    }
}
