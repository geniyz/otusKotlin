package site.geniyz.otus.biz.repo

import site.geniyz.otus.common.AppContext
import site.geniyz.otus.common.models.AppState
import site.geniyz.otus.common.repo.*
import site.geniyz.otus.cor.ICorChainDsl
import site.geniyz.otus.cor.worker

fun ICorChainDsl<AppContext>.repoTagPrepareDelete(title: String) = worker {
    this.title = title
    description = """
        Потдготовка к удалению из БД
    """.trimIndent()
    on { state == AppState.RUNNING }
    handle {
        tagRepoPrepare = tagValidated // .deepCopy()
    }
}
