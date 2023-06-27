package site.geniyz.otus.biz.groups

import site.geniyz.otus.cor.*
import site.geniyz.otus.common.*
import site.geniyz.otus.common.models.*

fun ICorChainDsl<AppContext>.operation(
    title: String,
    command: AppCommand,
    block: ICorChainDsl<AppContext>.() -> Unit
) = chain {
    block()
    this.title = title
    on { this.command == command && state == AppState.RUNNING }
}
