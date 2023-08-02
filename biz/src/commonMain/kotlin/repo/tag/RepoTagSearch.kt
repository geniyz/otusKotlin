package site.geniyz.otus.biz.repo

import site.geniyz.otus.common.AppContext
import site.geniyz.otus.common.models.AppState
import site.geniyz.otus.common.repo.*
import site.geniyz.otus.cor.ICorChainDsl
import site.geniyz.otus.cor.worker

fun ICorChainDsl<AppContext>.repoTagSearch(title: String) = worker {
    this.title = title
    description = "Поиск меток в БД по фильтру"
    on { state == AppState.RUNNING }
    handle {
        val request = DbTagFilterRequest(
            searchString = tagFilterValidated.searchString,
            ownerId = tagFilterValidated.ownerId,
        )
        val result = repo.searchTag(request)
        val resultTags = result.data
        if (result.isSuccess && resultTags != null) {
            tagsRepoDone = resultTags.toMutableList()
        } else {
            state = AppState.FAILING
            errors.addAll(result.errors)
        }
    }
}
