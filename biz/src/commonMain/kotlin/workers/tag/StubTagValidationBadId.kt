package site.geniyz.otus.biz.workers

import site.geniyz.otus.cor.ICorChainDsl
import site.geniyz.otus.cor.worker
import site.geniyz.otus.common.AppContext
import site.geniyz.otus.common.models.*
import site.geniyz.otus.common.stubs.AppStubs

fun ICorChainDsl<AppContext>.stubTagValidationBadId(title: String) = worker {
    this.title = title
    on { stubCase == AppStubs.BAD_ID && state == AppState.RUNNING }
    handle {
        state = AppState.FAILING
        this.errors.add(
            AppError(
                group = "validation-tag",
                code = "validation-id",
                field = "id",
                message = "Wrong id field"
            )
        )
    }
}
