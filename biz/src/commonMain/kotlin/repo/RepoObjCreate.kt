package site.geniyz.otus.biz.repo

import site.geniyz.otus.common.AppContext
import site.geniyz.otus.common.models.AppState
import site.geniyz.otus.common.repo.DbObjRequest
import site.geniyz.otus.cor.ICorChainDsl
import site.geniyz.otus.cor.worker

fun ICorChainDsl<AppContext>.repoObjCreate(title: String) = worker {
    this.title = title
    description = "Добавление сущности в БД"
    on {
        state == AppState.RUNNING
    }
    handle {
        val request = DbObjRequest(objRepoPrepare)
        val result = repo.createObj(request)
        val resultObj = result.data
        if (result.isSuccess && resultObj != null) {
            objRepoDone = resultObj
        } else {
            state = AppState.FAILING
            errors.addAll(result.errors)
        }
    }
}
