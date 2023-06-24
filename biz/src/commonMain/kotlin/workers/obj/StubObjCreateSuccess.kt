package site.geniyz.otus.biz.workers

import site.geniyz.otus.common.AppContext
import site.geniyz.otus.common.models.*
import site.geniyz.otus.common.stubs.AppStubs
import site.geniyz.otus.cor.*
import site.geniyz.otus.stubs.AppStubObjs

fun ICorChainDsl<AppContext>.stubObjCreateSuccess(title: String) = worker {
    this.title = title
    on { stubCase == AppStubs.SUCCESS && state == AppState.RUNNING }
    handle {
        state = AppState.FINISHING
        val stub = AppStubObjs.prepareResult {
            objRequest.name.takeIf { it.isNotBlank() }?.also { this.name = it }
            objRequest.content.takeIf { it.isNotBlank() }?.also { this.content = it }
            objRequest.objType.takeIf { it != AppObjType.NONE }?.also { this.objType = it }
        }
        objResponse = stub
    }
}
