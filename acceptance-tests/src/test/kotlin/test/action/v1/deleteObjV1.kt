package site.geniyz.otus.blackbox.test.action.v1

import io.kotest.assertions.asClue
import io.kotest.assertions.withClue
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

import site.geniyz.otus.api.v1.models.*
import site.geniyz.otus.blackbox.test.action.beValidId
import site.geniyz.otus.blackbox.fixture.client.Client

suspend fun Client.deleteObj(id: String?) {
    withClue("deleteObjV1: $id") {
        id should beValidId

        val response = sendAndReceive(
            "obj/delete",
            ObjDeleteRequest(
                requestType = "delete",
                debug = debug,
                obj = ObjDeleteObject(id = id)
            )
        ) as ObjDeleteResponse

        response.asClue {
            response should haveSuccessResult
            response.obj shouldNotBe null
            response.obj?.id shouldBe id
        }
    }
}