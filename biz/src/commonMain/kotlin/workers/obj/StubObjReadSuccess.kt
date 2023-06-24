package site.geniyz.otus.biz.workers

import site.geniyz.otus.common.AppContext
import site.geniyz.otus.common.models.*
import site.geniyz.otus.common.stubs.AppStubs
import site.geniyz.otus.cor.*
import site.geniyz.otus.stubs.AppStubObjs

fun ICorChainDsl<AppContext>.stubObjReadSuccess(title: String) = worker {
    this.title = title
    on { stubCase == AppStubs.SUCCESS && state == AppState.RUNNING }
    handle {
        state = AppState.FINISHING
        val stub = AppStubObjs.prepareResult {
            objRequest.id.takeIf { it != AppObjId.NONE }?.also { this.id = it }
        }
        objResponse = stub
    }
}
