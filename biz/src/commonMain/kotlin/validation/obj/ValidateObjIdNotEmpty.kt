package site.geniyz.otus.biz.validation

import site.geniyz.otus.common.AppContext
import site.geniyz.otus.common.helpers.errorValidation
import site.geniyz.otus.common.helpers.fail
import site.geniyz.otus.common.models.AppState
import site.geniyz.otus.cor.ICorChainDsl
import site.geniyz.otus.cor.worker

fun ICorChainDsl<AppContext>.validateObjIdNotEmpty(title: String) = worker {
    this.title = title
    on { objValidating.id.asString().isEmpty() }
    handle {
        fail(
            errorValidation(
                field = "id",
                violationCode = "empty",
                description = "field must not be empty",
                segment = "obj",
            )
        )
    }
}
