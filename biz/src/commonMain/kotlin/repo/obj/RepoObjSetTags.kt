package site.geniyz.otus.biz.repo

import site.geniyz.otus.biz.helpers.transliterate
import site.geniyz.otus.common.AppContext
import site.geniyz.otus.common.models.AppState
import site.geniyz.otus.common.repo.*
import site.geniyz.otus.cor.ICorChainDsl
import site.geniyz.otus.cor.worker

fun ICorChainDsl<AppContext>.repoObjSetTags(title: String) = worker {
    this.title = title
    description = "Изменение перечня меток объекта"
    on { state == AppState.RUNNING }
    handle {
        val request = DbObjSetTagsRequest(objRepoRead, tagsValidated).apply {
            tags = tags.map {
                it.copy(code = it.name.transliterate())
            }
        }
        val result = repo.setTags(request)
        val resultObj = result.data
        if (result.isSuccess && resultObj != null) {
            tagsRepoDone = resultObj.toMutableList()
            objRepoDone = objRepoRead
        } else {
            state = AppState.FAILING
            errors.addAll(result.errors)
            // objRepoDone = objRepoPrepare
        }
    }
}
