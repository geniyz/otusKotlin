package site.geniyz.otus.biz.repo

import site.geniyz.otus.common.AppContext
import site.geniyz.otus.common.models.AppState
import site.geniyz.otus.common.repo.*
import site.geniyz.otus.cor.ICorChainDsl
import site.geniyz.otus.cor.worker

fun ICorChainDsl<AppContext>.repoTagRead(title: String) = worker {
    this.title = title
    description = "Чтение метки из БД"
    on { state == AppState.RUNNING }
    handle {
        val request = DbTagIdRequest(tagValidated)
        val result = repo.readTag(request)
        val resultTag = result.data
        if (result.isSuccess && resultTag != null) {
            tagRepoRead = resultTag
        } else {
            state = AppState.FAILING
            errors.addAll(result.errors)
        }
    }
}
