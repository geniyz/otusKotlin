package site.geniyz.otus.biz.workers

import site.geniyz.otus.common.AppContext
import site.geniyz.otus.common.models.*
import site.geniyz.otus.cor.*

fun ICorChainDsl<AppContext>.initStatus(title: String) = worker() {
    this.title = title
    on { state == AppState.NONE }
    handle { state = AppState.RUNNING }
}
