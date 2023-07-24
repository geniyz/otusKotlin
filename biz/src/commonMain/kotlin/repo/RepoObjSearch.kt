package site.geniyz.otus.biz.repo

import site.geniyz.otus.common.AppContext
import site.geniyz.otus.common.models.AppState
import site.geniyz.otus.common.repo.*
import site.geniyz.otus.cor.ICorChainDsl
import site.geniyz.otus.cor.worker

fun ICorChainDsl<AppContext>.repoObjSearch(title: String) = worker {
    this.title = title
    description = "Поиск объектов в БД по фильтру"
    on { state == AppState.RUNNING }
    handle {
        val request = DbObjFilterRequest(
            searchString = objFilterValidated.searchString,
            ownerId = objFilterValidated.ownerId,
        )
        val result = repo.searchObj(request)
        val resultObjs = result.data
        if (result.isSuccess && resultObjs != null) {
            objsRepoDone = resultObjs.toMutableList()
        } else {
            state = AppState.FAILING
            errors.addAll(result.errors)
        }
    }
}
