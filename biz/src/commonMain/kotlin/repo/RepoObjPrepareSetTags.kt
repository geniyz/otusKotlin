package site.geniyz.otus.biz.repo

import site.geniyz.otus.common.AppContext
import site.geniyz.otus.common.models.AppState
import site.geniyz.otus.common.repo.*
import site.geniyz.otus.cor.ICorChainDsl
import site.geniyz.otus.cor.worker

fun ICorChainDsl<AppContext>.repoObjPrepareSetTags(title: String) = worker {
    this.title = title
    description = "Подготовка данных к изменению меток объекта"
    on { state == AppState.RUNNING }
    handle {
        objRepoPrepare = objRepoRead // .deepCopy()
        objRepoDone = objRepoRead // .deepCopy()
    }
}
