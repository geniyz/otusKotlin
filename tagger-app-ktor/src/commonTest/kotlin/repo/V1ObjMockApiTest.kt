package site.geniyz.otus.app.repo

import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.config.*
import io.ktor.server.testing.*
import io.ktor.websocket.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import site.geniyz.otus.api.v1.apiV1Mapper
import site.geniyz.otus.backend.repo.tests.RepositoryMock
import site.geniyz.otus.api.v1.models.*
import site.geniyz.otus.app.AppSettings
import site.geniyz.otus.app.auth.AuthConfig
import site.geniyz.otus.app.auth.addAuth
import site.geniyz.otus.app.module
import site.geniyz.otus.common.CorSettings
import site.geniyz.otus.common.models.*
import site.geniyz.otus.common.repo.*
import site.geniyz.otus.stubs.AppStubObjs
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class V1ObjMockApiTest {
    private val stub = AppStubObjs.getText()
    private val userId = stub.authorId
    private val objId = stub.id

    @Test
    fun create() = testApplication {
        initRepoTest(RepositoryMock(
            invokeCreateObj = {
                DbObjResponse(
                    isSuccess = true,
                    data = it.obj.copy(id = objId),
                )
            }
        ))

        val client = myClient()

        val createObj = ObjCreateObject(
            name = "Название",
            content = "Описание",
            objType = ObjType.TEXT,
        )

        val response = client.post("/v1/obj/create") {
            val requestObj = ObjCreateRequest(
                requestId = "12345",
                obj = createObj,
                debug = ObjDebug(
                    mode = RequestDebugMode.TEST,
                )
            )
            contentType(ContentType.Application.Json)
            addAuth(id = "123", config = AuthConfig.TEST)

            setBody(apiV1Mapper.encodeToString(requestObj))
        }
        val responseObj = apiV1Mapper.decodeFromString<ObjCreateResponse>(response.bodyAsText())

        assertEquals(200, response.status.value)
        assertEquals(objId.asString(), responseObj.obj?.id)
        assertEquals(createObj.name, responseObj.obj?.name)
        assertEquals(createObj.content, responseObj.obj?.content)
        assertEquals(createObj.objType, responseObj.obj?.objType)
    }

    @Test
    fun read() = testApplication {
        initRepoTest(RepositoryMock(
            invokeReadObj = {
                DbObjResponse(
                    isSuccess = true,
                    data = AppObj(
                        id = it.id,
                        authorId = userId,
                    ),
                )
            }
        ))

        val client = myClient()

        val response = client.post("/v1/obj/read") {
            val requestObj = ObjReadRequest(
                requestId = "12345",
                obj = ObjReadObject(objId.asString()),
                debug = ObjDebug(
                    mode = RequestDebugMode.TEST,
                )
            )
            contentType(ContentType.Application.Json)
            addAuth(id = "123", config = AuthConfig.TEST)
            setBody(apiV1Mapper.encodeToString(requestObj))
        }
        val responseObj = apiV1Mapper.decodeFromString<ObjReadResponse>(response.bodyAsText())
        assertEquals(200, response.status.value)
        assertEquals(objId.asString(), responseObj.obj?.id)
    }

    @Test
    fun update() = testApplication {
        initRepoTest(RepositoryMock(
            invokeReadObj = {
                DbObjResponse(
                    isSuccess = true,
                    data = AppObj(
                        id = it.id,
                        authorId = userId,
                        lock = AppLock("123"),
                    ),
                )
            },
            invokeUpdateObj = {
                DbObjResponse(
                    isSuccess = true,
                    data = it.obj.copy(authorId = userId),
                )
            }
        ))

        val client = myClient()

        val objUpdate = ObjUpdateObject(
            id = "666",
            name = "Вещь",
            content = "Содержимое",
            objType = ObjType.TEXT,
            lock = "123",
        )

        val response = client.post("/v1/obj/update") {
            val requestObj = ObjUpdateRequest(
                requestId = "12345",
                obj = ObjUpdateObject(
                    id = "666",
                    name = "Объект",
                    content = "Содержимое",
                    objType = ObjType.TEXT,
                    lock = "123",
                ),
                debug = ObjDebug(
                    mode = RequestDebugMode.TEST,
                )
            )
            contentType(ContentType.Application.Json)
            addAuth(id = "123", config = AuthConfig.TEST)
            setBody(apiV1Mapper.encodeToString(requestObj))
        }
        val responseObj = apiV1Mapper.decodeFromString<ObjUpdateResponse>(response.bodyAsText())
        assertEquals(200, response.status.value)
        assertEquals(objUpdate.id, responseObj.obj?.id)
        assertEquals(objUpdate.name, responseObj.obj?.name)
        assertEquals(objUpdate.content, responseObj.obj?.content)
        assertEquals(objUpdate.objType, responseObj.obj?.objType)
    }

    @Test
    fun delete() = testApplication {
        initRepoTest(RepositoryMock(
            invokeReadObj = {
                DbObjResponse(
                    isSuccess = true,
                    data = AppObj(
                        id = it.id,
                        authorId = userId,
                    ),
                )
            },
            invokeDeleteObj = {
                DbObjResponse(
                    isSuccess = true,
                    data = AppObj(
                        id = it.id,
                        authorId = userId,
                    ),
                )
            }
        ))

        val client = myClient()

        val deleteId = "666"

        val response = client.post("/v1/obj/delete") {
            val requestObj = ObjDeleteRequest(
                requestId = "12345",
                obj = ObjDeleteObject(
                    id = deleteId,
                    lock = "123",
                ),
                debug = ObjDebug(
                    mode = RequestDebugMode.TEST,
                )
            )
            contentType(ContentType.Application.Json)
            addAuth(id = "123", config = AuthConfig.TEST)
            setBody(apiV1Mapper.encodeToString(requestObj))
        }
        val responseObj = apiV1Mapper.decodeFromString<ObjDeleteResponse>(response.bodyAsText())
        assertEquals(200, response.status.value)
        assertEquals(deleteId, responseObj.obj?.id)
    }

    @Test
    fun search() = testApplication {
        initRepoTest(RepositoryMock(
            invokeSearchObj = {
                DbObjsResponse(
                    isSuccess = true,
                    data = listOf(
                        AppObj(
                            name = it.searchString,
                            authorId = it.ownerId,
                        )
                    ),
                )
            }
        ))
        val client = myClient()

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
            setBody(apiV1Mapper.encodeToString(requestObj))
        }
        val responseObj = apiV1Mapper.decodeFromString<ObjSearchResponse>(response.bodyAsText())
        assertEquals(200, response.status.value)
        assertNotEquals(0, responseObj.objs?.size)
    }

    private fun ApplicationTestBuilder.myClient() = createClient {
        install(ContentNegotiation) {
            json(Json)
        }
    }

    private fun ApplicationTestBuilder.initRepoTest(repo: IRepository) {
        environment {
            config = MapApplicationConfig()
        }
        application {
            module(AppSettings(corSettings = CorSettings(repoTest = repo)))
        }
    }
}
