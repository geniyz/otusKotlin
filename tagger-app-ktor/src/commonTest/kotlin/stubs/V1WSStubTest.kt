package site.geniyz.otus.app.stubs

import io.ktor.client.plugins.websocket.*
import io.ktor.server.testing.*
import io.ktor.websocket.*
import kotlinx.coroutines.withTimeout
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString

import site.geniyz.otus.api.v1.apiV1Mapper
import site.geniyz.otus.api.v1.models.*

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class V1WSStubTest {

    @Test
    fun createStub() {
        val reqID = "012345-ObjCreateRequest"

        val request = ObjCreateRequest(
            requestId = reqID,
            obj = ObjCreateObject(
                name = "Название",
                content = "Содержимое",
                objType = ObjType.TEXT
            ),
            debug = ObjDebug(
                mode = RequestDebugMode.STUB,
                stub = ObjRequestDebugStubs.SUCCESS
            )
        )

        testMethod<IResponse>(request) {
            assertEquals(reqID, it.requestId)
        }
    }

    @Test
    fun readStub() {
        val reqID = "012345-ObjReadRequest"

        val request = ObjReadRequest(
            requestId = reqID,
            obj = ObjReadObject("777"),
            debug = ObjDebug(
                mode = RequestDebugMode.STUB,
                stub = ObjRequestDebugStubs.SUCCESS
            )
        )

        testMethod<IResponse>(request) {
            assertEquals(reqID, it.requestId)
        }
    }

    @Test
    fun updateStub() {
        val reqID = "012345-ObjUpdateRequest"

        val request = ObjUpdateRequest(
            requestId = reqID,
            obj = ObjUpdateObject(
                id = "777",
                name = "Название",
                content = "Содержимое",
                objType = ObjType.TEXT
            ),
            debug = ObjDebug(
                mode = RequestDebugMode.STUB,
                stub = ObjRequestDebugStubs.SUCCESS
            )
        )

        testMethod<IResponse>(request) {
            assertEquals(reqID, it.requestId)
        }
   }

    @Test
    fun deleteStub() {
        val reqID = "012345-ObjDeleteRequest"

        val request = ObjDeleteRequest(
            requestId = reqID,
            obj = ObjDeleteObject(
                id = "777",
            ),
            debug = ObjDebug(
                mode = RequestDebugMode.STUB,
                stub = ObjRequestDebugStubs.SUCCESS
            )
        )

        testMethod<IResponse>(request) {
            assertEquals(reqID, it.requestId)
        }
    }

    @Test
    fun searchStub() {
        val reqID = "012345-ObjSearchRequest"

        val request = ObjSearchRequest(
            requestId = reqID,
            objFilter = ObjSearchFilter("найдись"),
            debug = ObjDebug(
                mode = RequestDebugMode.STUB,
                stub = ObjRequestDebugStubs.SUCCESS
            )
        )

        testMethod<IResponse>(request) {
            assertEquals(reqID, it.requestId)
        }
    }

    @Test
    fun offersStub() {
        val reqID = "012345-ObjListTagsRequest"

        val request = ObjListTagsRequest(
            requestId = reqID,
            obj = ObjListTagsObject(
                id = "777",
            ),
            debug = ObjDebug(
                mode = RequestDebugMode.STUB,
                stub = ObjRequestDebugStubs.SUCCESS
            )
        )

        testMethod<IResponse>(request) {
            assertEquals(reqID, it.requestId)
        }
    }

    private inline fun <reified T> testMethod(
        request: IRequest,
        crossinline assertBlock: (T) -> Unit
    ) = testApplication {
        val client = createClient {
            install(WebSockets)
        }

        client.webSocket("/ws/v1") {
            withTimeout(2000) {
                val incame = incoming.receive() as Frame.Text
                val response = apiV1Mapper.decodeFromString<T>(incame.readText())
                assertIs<ObjInitResponse>(response)
            }
            send(Frame.Text(apiV1Mapper.encodeToString(request)))
            withTimeout(2000) {
                val incame = incoming.receive() as Frame.Text
                val text = incame.readText()
                val response = apiV1Mapper.decodeFromString<T>(text)

                assertBlock(response)
            }
        }
    }
}
