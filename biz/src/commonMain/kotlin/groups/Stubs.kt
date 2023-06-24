package site.geniyz.otus.biz.groups

import site.geniyz.otus.common.*
import site.geniyz.otus.common.models.*

import site.geniyz.otus.cor.*


fun ICorChainDsl<AppContext>.stubs(title: String, block: ICorChainDsl<AppContext>.() -> Unit) = chain {
    block()
    this.title = title
    on { workMode == AppWorkMode.STUB && state == AppState.RUNNING }
}
