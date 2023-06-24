package site.geniyz.otus.biz.validation

import site.geniyz.otus.common.AppContext
import site.geniyz.otus.common.models.AppState
import site.geniyz.otus.cor.ICorChainDsl
import site.geniyz.otus.cor.chain

fun ICorChainDsl<AppContext>.validation(block: ICorChainDsl<AppContext>.() -> Unit) = chain {
    block()
    title = "Валидация"

    on { state == AppState.RUNNING }
}
