package site.geniyz.otus.biz.workers

import site.geniyz.otus.common.AppContext
import site.geniyz.otus.common.helpers.fail
import site.geniyz.otus.common.models.*
import site.geniyz.otus.cor.*

fun ICorChainDsl<AppContext>.stubNoCase(title: String) = worker {
    this.title = title
    on { state == AppState.RUNNING }
    handle {
        fail(
            AppError(
                code = "validation",
                field = "stub",
                group = "validation",
                message = "Wrong stub case is requested: ${stubCase.name}"
            )
        )
    }
}
