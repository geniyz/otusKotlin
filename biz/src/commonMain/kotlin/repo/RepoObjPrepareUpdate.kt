package site.geniyz.otus.biz.repo

import site.geniyz.otus.common.AppContext
import site.geniyz.otus.common.models.AppState
import site.geniyz.otus.common.repo.*
import site.geniyz.otus.cor.ICorChainDsl
import site.geniyz.otus.cor.worker

fun ICorChainDsl<AppContext>.repoPrepareObjUpdate(title: String) = worker {
    this.title = title
    description = "Готовим данные к сохранению в БД: совмещаем данные, прочитанные из БД, " +
            "и данные, полученные от пользователя"
    on { state == AppState.RUNNING }
    handle {
        objRepoPrepare = objRepoRead/*.deepCopy()*/.apply {
            name    = objValidated.name
            content = objValidated.content
            objType = objValidated.objType
        }
    }
}
