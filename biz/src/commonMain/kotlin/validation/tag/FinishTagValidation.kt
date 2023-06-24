package site.geniyz.otus.biz.validation

import site.geniyz.otus.common.AppContext
import site.geniyz.otus.common.models.AppState
import site.geniyz.otus.cor.ICorChainDsl
import site.geniyz.otus.cor.worker

fun ICorChainDsl<AppContext>.finishTagValidation(title: String) = worker {
    this.title = title
    on { state == AppState.RUNNING }
    handle {
        tagValidated = tagValidating
    }
}

fun ICorChainDsl<AppContext>.finishTagFilterValidation(title: String) = worker {
    this.title = title
    on { state == AppState.RUNNING }
    handle {
        tagFilterValidated = tagFilterValidating
    }
}
