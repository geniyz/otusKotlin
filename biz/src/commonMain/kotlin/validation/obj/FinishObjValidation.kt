package site.geniyz.otus.biz.validation

import site.geniyz.otus.common.AppContext
import site.geniyz.otus.common.models.AppState
import site.geniyz.otus.cor.ICorChainDsl
import site.geniyz.otus.cor.worker

fun ICorChainDsl<AppContext>.finishObjValidation(title: String) = worker {
    this.title = title
    on { state == AppState.RUNNING }
    handle {
        objValidated  = objValidating
        tagsValidated = tagsValidating.toMutableList()
    }
}

fun ICorChainDsl<AppContext>.finishObjFilterValidation(title: String) = worker {
    this.title = title
    on { state == AppState.RUNNING }
    handle {
        objFilterValidated = objFilterValidating
    }
}
