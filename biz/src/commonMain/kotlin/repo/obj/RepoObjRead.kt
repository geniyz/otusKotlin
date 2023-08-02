package site.geniyz.otus.biz.repo

import site.geniyz.otus.common.AppContext
import site.geniyz.otus.common.models.AppState
import site.geniyz.otus.common.repo.*
import site.geniyz.otus.cor.ICorChainDsl
import site.geniyz.otus.cor.worker

fun ICorChainDsl<AppContext>.repoObjRead(title: String) = worker {
    this.title = title
    description = "Чтение объекта из БД"
    on { state == AppState.RUNNING }
    handle {
        val request = DbObjIdRequest(objValidated)
        val result = repo.readObj(request)
        val resultObj = result.data
        if (result.isSuccess && resultObj != null) {
            objRepoRead = resultObj
        } else {
            state = AppState.FAILING
            errors.addAll(result.errors)
        }
    }
}
