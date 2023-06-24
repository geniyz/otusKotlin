package site.geniyz.otus.biz.validation

import site.geniyz.otus.common.AppContext
import site.geniyz.otus.common.helpers.errorValidation
import site.geniyz.otus.common.helpers.fail
import site.geniyz.otus.common.models.AppState
import site.geniyz.otus.cor.ICorChainDsl
import site.geniyz.otus.cor.worker

fun ICorChainDsl<AppContext>.validateTagNameNotEmpty(title: String) = worker {
    this.title = title
    on { tagValidating.name.isEmpty() }
    handle {
        fail(
            errorValidation(
                field = "name",
                violationCode = "empty",
                description = "field must not be empty",
                segment = "tag",
            )
        )
    }
}
