package site.geniyz.otus.biz.workers

import site.geniyz.otus.common.AppContext
import site.geniyz.otus.common.models.*
import site.geniyz.otus.common.stubs.AppStubs
import site.geniyz.otus.cor.*
import site.geniyz.otus.stubs.AppStubObjs
import site.geniyz.otus.stubs.AppStubTags

fun ICorChainDsl<AppContext>.stubTagListObjsSuccess(title: String) = worker {
    this.title = title
    on { stubCase == AppStubs.SUCCESS && state == AppState.RUNNING }
    handle {
        state = AppState.FINISHING
        tagResponse = AppStubTags.prepareResult {
            tagRequest.id.takeIf { it != AppTagId.NONE }?.also { this.id = it }
        }
        objsResponse.addAll(AppStubObjs.prepareSearchList(""))
    }
}
