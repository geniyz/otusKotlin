package site.geniyz.otus.blackbox.test.action.v1

import io.kotest.assertions.asClue
import io.kotest.assertions.withClue
import io.kotest.matchers.should
import io.kotest.matchers.shouldNotBe

import site.geniyz.otus.api.v1.models.ObjReadObject
import site.geniyz.otus.api.v1.models.ObjReadRequest
import site.geniyz.otus.api.v1.models.ObjReadResponse
import site.geniyz.otus.api.v1.models.ObjResponseObject
import site.geniyz.otus.blackbox.test.action.beValidId
import site.geniyz.otus.blackbox.fixture.client.Client

suspend fun Client.readObj(id: String?): ObjResponseObject = readObj(id) {
    it should haveSuccessResult
    it.obj shouldNotBe null
    it.obj!!
}

suspend fun <T> Client.readObj(id: String?, block: (ObjReadResponse) -> T): T =
    withClue("readObjV1: $id") {
        id should beValidId

        val response = sendAndReceive(
            "obj/read",
            ObjReadRequest(
                requestType = "read",
                debug = debug,
                obj = ObjReadObject(id = id)
            )
        ) as ObjReadResponse

        response.asClue(block)
    }
