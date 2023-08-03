package site.geniyz.otus.biz.validation

import site.geniyz.otus.common.helpers.errorValidation
import site.geniyz.otus.common.AppContext
import site.geniyz.otus.common.helpers.fail
import site.geniyz.otus.cor.ICorChainDsl
import site.geniyz.otus.cor.worker

fun ICorChainDsl<AppContext>.validateLockNotEmpty(title: String) = worker {
    this.title = title
    on {
        objValidating.lock.asString().isEmpty() &&
                tagValidating.lock.asString().isEmpty()
    }
    handle {
        fail(
            errorValidation(
                field = "lock",
                violationCode = "empty",
                description = "field must not be empty"
            )
        )
    }
}
