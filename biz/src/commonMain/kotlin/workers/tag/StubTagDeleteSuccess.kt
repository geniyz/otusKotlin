package site.geniyz.otus.biz.workers

import site.geniyz.otus.common.AppContext
import site.geniyz.otus.common.models.*
import site.geniyz.otus.common.stubs.AppStubs
import site.geniyz.otus.cor.*
import site.geniyz.otus.stubs.AppStubTags

fun ICorChainDsl<AppContext>.stubTagDeleteSuccess(title: String) = worker {
    this.title = title
    on { stubCase == AppStubs.SUCCESS && state == AppState.RUNNING }
    handle {
        state = AppState.FINISHING
        val stub = AppStubTags.prepareResult {
            tagRequest.code.takeIf { it.isNotBlank() }?.also { this.code = it }
            tagRequest.name.takeIf { it.isNotBlank() }?.also { this.name = it }
        }
        tagResponse = stub
    }
}
