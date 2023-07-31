package site.geniyz.otus.biz.validation

import site.geniyz.otus.common.AppContext
import site.geniyz.otus.common.helpers.errorValidation
import site.geniyz.otus.common.helpers.fail
import site.geniyz.otus.common.models.AppLock
import site.geniyz.otus.cor.ICorChainDsl
import site.geniyz.otus.cor.worker

fun ICorChainDsl<AppContext>.validateLockProperFormat(title: String) = worker {
    this.title = title

    val regExp = Regex("^[0-9a-zA-Z-]+$")

    on {
        (objValidating.lock != AppLock.NONE && !objValidating.lock.asString().matches(regExp)) ||
                (tagValidating.lock != AppLock.NONE && !tagValidating.lock.asString().matches(regExp))
    }
    handle {
        fail(
            errorValidation(
                field = "lock",
                violationCode = "badFormat",
                description = "Lock must contain only"
            )
        )
    }
}
