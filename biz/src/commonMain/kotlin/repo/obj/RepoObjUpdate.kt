package site.geniyz.otus.biz.repo

import site.geniyz.otus.common.AppContext
import site.geniyz.otus.common.models.AppState
import site.geniyz.otus.common.repo.*
import site.geniyz.otus.cor.ICorChainDsl
import site.geniyz.otus.cor.worker

fun ICorChainDsl<AppContext>.repoObjUpdate(title: String) = worker {
    this.title = title
    on { state == AppState.RUNNING }
    handle {
        val request = DbObjRequest(objRepoPrepare)
        val result = repo.updateObj(request)
        val resultObj = result.data
        if (result.isSuccess && resultObj != null) {
            objRepoDone = resultObj
        } else {
            state = AppState.FAILING
            errors.addAll(result.errors)
            // objRepoDone = objRepoPrepare
        }
    }
}
