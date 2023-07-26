package site.geniyz.otus.biz.validation

import site.geniyz.otus.common.AppContext
import site.geniyz.otus.common.helpers.errorValidation
import site.geniyz.otus.common.helpers.fail
import site.geniyz.otus.cor.ICorChainDsl
import site.geniyz.otus.cor.worker

fun ICorChainDsl<AppContext>.validateObjContentHasContent(title: String) = worker {
    this.title = title
    val regExp = Regex("\\p{L}")
    on { objValidating.content.isNotEmpty() && !objValidating.content.contains(regExp) }
    handle {
        fail(
            errorValidation(
                field = "content",
                violationCode = "noContent",
                description = "field must contain letters",
                segment = "obj",
            )
        )
    }
}
