package site.geniyz.otus.biz.workers

import site.geniyz.otus.common.AppContext
import site.geniyz.otus.common.models.*
import site.geniyz.otus.common.stubs.AppStubs
import site.geniyz.otus.cor.*
import site.geniyz.otus.stubs.AppStubObjs
import site.geniyz.otus.stubs.AppStubTags

fun ICorChainDsl<AppContext>.stubObjSetTagsSuccess(title: String) = worker {
    this.title = title
    on { stubCase == AppStubs.SUCCESS && state == AppState.RUNNING }
    handle {
        state = AppState.FINISHING
        objResponse = AppStubObjs.prepareResult {
            objRequest.id.takeIf { it != AppObjId.NONE }?.also { this.id = it }
        }

        tagsResponse.addAll( tagsRequest.map {
                AppStubTags.prepareResult {
                    it.id.takeIf { it != AppTagId.NONE }?.also { this.id = it }
                    it.code.takeIf { it.isNotBlank() }?.also { this.code = it }
                    it.name.takeIf { it.isNotBlank() }?.also { this.name = it }
                }
        })
    }
}
