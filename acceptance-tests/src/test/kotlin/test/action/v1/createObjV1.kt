package site.geniyz.otus.blackbox.test.action.v1

import io.kotest.assertions.asClue
import io.kotest.assertions.withClue
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

import site.geniyz.otus.api.v1.models.*
import site.geniyz.otus.blackbox.fixture.client.Client

suspend fun Client.createObj(obj: ObjCreateObject = someCreateObj): ObjResponseObject = createObj(obj) {
    it should haveSuccessResult
    it.obj shouldNotBe null
    it.obj?.apply {
        name shouldBe obj.name
        content shouldBe obj.content
        objType shouldBe obj.objType
    }
    it.obj!!
}

suspend fun <T> Client.createObj(obj: ObjCreateObject = someCreateObj, block: (ObjCreateResponse) -> T): T =
    withClue("createObjV1: $obj") {
        val response = sendAndReceive(
            "obj/create", ObjCreateRequest(
                requestType = "objCreate",
                debug = debug,
                obj = obj
            )
        ) as ObjCreateResponse

        response.asClue(block)
    }
