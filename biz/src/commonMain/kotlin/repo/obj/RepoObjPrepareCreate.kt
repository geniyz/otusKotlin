package site.geniyz.otus.biz.repo

import site.geniyz.otus.common.AppContext
import site.geniyz.otus.common.models.AppState
import site.geniyz.otus.common.repo.*
import site.geniyz.otus.cor.ICorChainDsl
import site.geniyz.otus.cor.worker

fun ICorChainDsl<AppContext>.repoObjPrepareCreate(title: String) = worker {
    this.title = title
    description = "Подготовка объекта к сохранению в базе данных"
    on { state == AppState.RUNNING }
    handle {
        objRepoRead    = objValidated // .copy()
        objRepoRead.authorId = principal.id
        objRepoPrepare = objRepoRead
    }
}
