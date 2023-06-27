package site.geniyz.otus.biz.workers

import site.geniyz.otus.cor.ICorChainDsl
import site.geniyz.otus.cor.worker
import site.geniyz.otus.common.AppContext
import site.geniyz.otus.common.models.*
import site.geniyz.otus.common.stubs.AppStubs

fun ICorChainDsl<AppContext>.stubObjValidationBadName(title: String) = worker {
    this.title = title
    on { stubCase == AppStubs.BAD_NAME && state == AppState.RUNNING }
    handle {
        state = AppState.FAILING
        this.errors.add(
            AppError(
                group = "validation-obj",
                code = "validation-name",
                field = "name",
                message = "Wrong name field"
            )
        )
    }
}
