package site.geniyz.otus.biz.repo

import site.geniyz.otus.common.AppContext
import site.geniyz.otus.common.models.AppState
import site.geniyz.otus.common.repo.*
import site.geniyz.otus.cor.ICorChainDsl
import site.geniyz.otus.cor.worker

fun ICorChainDsl<AppContext>.repoObjListTags(title: String) = worker {
    this.title = title
    description = "Перечень меток объекта"
    on { state == AppState.RUNNING }
    handle {
        val request = DbLnkFilterRequest(
            obj = objRepoRead.id.asString(), // objValidated.id.asString(),
            ownerId = objRepoRead.authorId,  // objValidated.authorId,
        )
        val result = repo.searchTags(request)
        val resultObj = result.data
        if (result.isSuccess && resultObj != null) {
            tagsRepoDone = resultObj.toMutableList()
            objRepoDone = objRepoRead
        } else {
            state = AppState.FAILING
            errors.addAll(result.errors)
        }
    }
}
