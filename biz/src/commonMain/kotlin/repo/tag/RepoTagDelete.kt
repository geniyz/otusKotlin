package site.geniyz.otus.biz.repo

import site.geniyz.otus.common.AppContext
import site.geniyz.otus.common.models.AppState
import site.geniyz.otus.common.repo.*
import site.geniyz.otus.cor.ICorChainDsl
import site.geniyz.otus.cor.worker

fun ICorChainDsl<AppContext>.repoTagDelete(title: String) = worker {
    this.title = title
    description = "Удаление метки из БД по ID"
    on { state == AppState.RUNNING }
    handle {
        val request = DbTagIdRequest(tagRepoPrepare)
        val result = repo.deleteTag(request)
        if (!result.isSuccess) {
            state = AppState.FAILING
            errors.addAll(result.errors)
        }
        tagRepoDone = tagRepoRead
    }
}
