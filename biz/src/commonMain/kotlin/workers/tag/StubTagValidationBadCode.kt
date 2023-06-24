package site.geniyz.otus.biz.workers

import site.geniyz.otus.cor.ICorChainDsl
import site.geniyz.otus.cor.worker
import site.geniyz.otus.common.AppContext
import site.geniyz.otus.common.models.*
import site.geniyz.otus.common.stubs.AppStubs

fun ICorChainDsl<AppContext>.stubTagValidationBadCode(title: String) = worker {
    this.title = title
    on { stubCase == AppStubs.BAD_CODE && state == AppState.RUNNING }
    handle {
        state = AppState.FAILING
        this.errors.add(
            AppError(
                group = "validation-tag",
                code = "validation-code",
                field = "code",
                message = "Wrong code field"
            )
        )
    }
}
