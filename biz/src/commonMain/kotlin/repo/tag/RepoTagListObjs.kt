package site.geniyz.otus.biz.repo

import site.geniyz.otus.common.AppContext
import site.geniyz.otus.common.models.AppState
import site.geniyz.otus.common.repo.*
import site.geniyz.otus.cor.ICorChainDsl
import site.geniyz.otus.cor.worker

fun ICorChainDsl<AppContext>.repoTagListObjs(title: String) = worker {
    this.title = title
    description = "Перечень объектов с заданной меткой"
    on { state == AppState.RUNNING }
    handle {
        val request = DbLnkFilterRequest(
            tag = tagValidated.id.asString(),
            ownerId = objValidated.authorId,
        )
        val result = repo.searchObjs(request)
        val resultObjs = result.data
        if (result.isSuccess && resultObjs != null) {
            objsRepoDone = resultObjs.toMutableList()
            tagRepoDone = repo.readTag( DbTagIdRequest(tagValidated) ).data!!
        } else {
            state = AppState.FAILING
            errors.addAll(result.errors)
        }
    }
}
