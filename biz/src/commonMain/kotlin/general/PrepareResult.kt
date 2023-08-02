package site.geniyz.otus.biz.general

import site.geniyz.otus.common.AppContext
import site.geniyz.otus.common.models.*
import site.geniyz.otus.cor.ICorChainDsl
import site.geniyz.otus.cor.worker

fun ICorChainDsl<AppContext>.prepareResult(title: String) = worker {
    this.title = title
    description = "Подготовка данных для ответа клиенту на запрос"
    on { workMode != AppWorkMode.STUB }
    handle {
        if(objRepoDone != AppObj.NONE) objResponse = objRepoDone
        if(objsRepoDone.isNotEmpty() ) objsResponse = objsRepoDone

        if(tagRepoDone != AppTag.NONE) tagResponse = tagRepoDone
        if(tagsRepoDone.isNotEmpty() ) tagsResponse = tagsRepoDone

        state = when (val st = state) {
            AppState.RUNNING -> AppState.FINISHING
            else -> st
        }
    }
}
