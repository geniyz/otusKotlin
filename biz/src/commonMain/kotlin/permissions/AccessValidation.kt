package site.geniyz.otus.biz.permissions

import site.geniyz.otus.auth.*
import site.geniyz.otus.common.AppContext
import site.geniyz.otus.common.helpers.fail
import site.geniyz.otus.common.models.*
import site.geniyz.otus.cor.*

fun ICorChainDsl<AppContext>.accessValidation(title: String) = chain {
    this.title = title
    description = "Вычисление прав доступа по группе принципала и таблице прав доступа"
    on { state == AppState.RUNNING }
    worker("Вычисление отношения объекта к принципалу") {
        objRepoRead.principalRelations = objRepoRead.resolveRelationsTo(principal)
    }
    worker("Вычисление доступа к объявлению") {
        permitted = checkPermitted(command, objRepoRead.principalRelations, permissionsChain)
    }
    worker {
        this.title = "Валидация прав доступа"
        description = "Проверка наличия прав для выполнения операции"
        on { !permitted }
        handle {
            fail(AppError(message = "User is not allowed to perform this operation"))
        }
    }
}
