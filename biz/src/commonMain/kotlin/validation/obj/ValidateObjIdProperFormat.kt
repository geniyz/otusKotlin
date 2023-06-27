package site.geniyz.otus.biz.validation

import site.geniyz.otus.common.AppContext
import site.geniyz.otus.common.helpers.errorValidation
import site.geniyz.otus.common.helpers.fail
import site.geniyz.otus.common.models.AppObjId
import site.geniyz.otus.common.models.AppState
import site.geniyz.otus.cor.ICorChainDsl
import site.geniyz.otus.cor.worker

fun ICorChainDsl<AppContext>.validateObjIdProperFormat(title: String) = worker {
    this.title = title

    val regExp = Regex("^[0-9a-zA-Z-]+$")
    on { objValidating.id != AppObjId.NONE && !objValidating.id.asString().matches(regExp) }
    handle {
        val encodedId = objValidating.id.asString()
            .replace("<", "&lt;")
            .replace(">", "&gt;")
        fail(
            errorValidation(
                field = "id",
                violationCode = "badFormat",
                description = "value $encodedId must contain only letters and numbers",
                segment = "obj"
            )
        )
    }
}
