package site.geniyz.otus.blackbox.test.action.v1

import io.kotest.assertions.asClue
import io.kotest.assertions.withClue
import io.kotest.matchers.should

import site.geniyz.otus.api.v1.models.ObjResponseObject
import site.geniyz.otus.api.v1.models.ObjSearchFilter
import site.geniyz.otus.api.v1.models.ObjSearchRequest
import site.geniyz.otus.api.v1.models.ObjSearchResponse
import site.geniyz.otus.blackbox.fixture.client.Client

suspend fun Client.searchObj(search: ObjSearchFilter): List<ObjResponseObject> = searchObj(search) {
    it should haveSuccessResult
    it.objs ?: listOf()
}

suspend fun <T> Client.searchObj(search: ObjSearchFilter, block: (ObjSearchResponse) -> T): T =
    withClue("searchAObjV1: $search") {
        val response = sendAndReceive(
            "obj/search",
            ObjSearchRequest(
                requestType = "search",
                debug = debug,
                objFilter = search,
            )
        ) as ObjSearchResponse

        response.asClue(block)
    }
