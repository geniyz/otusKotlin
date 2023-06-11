package site.geniyz.otus.blackbox.test.action.v1

import io.kotest.assertions.asClue
import io.kotest.assertions.withClue
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

import site.geniyz.otus.api.v1.models.*
import site.geniyz.otus.blackbox.test.action.beValidId
import site.geniyz.otus.blackbox.fixture.client.Client

suspend fun Client.updateObj(id: String?, obj: ObjUpdateObject): ObjResponseObject =
    updateObj(id, obj) {
        it should haveSuccessResult
        it.obj shouldNotBe null
        it.obj?.apply {
            if (obj.name != null)
                name shouldBe obj.name
            if (obj.content != null)
                content shouldBe obj.content
            if (obj.objType != null)
                objType shouldBe obj.objType
        }
        it.obj!!
    }

suspend fun <T> Client.updateObj(id: String?, obj: ObjUpdateObject, block: (ObjUpdateResponse) -> T): T =
    withClue("updatedV1: $id, set: $obj") {
        id should beValidId

        val response = sendAndReceive(
            "obj/update", ObjUpdateRequest(
                requestType = "objUpdate",
                debug = debug,
                obj = obj.copy(id = id)
            )
        ) as ObjUpdateResponse

        response.asClue(block)
    }
