package site.geniyz.otus.biz.permissions

import site.geniyz.otus.common.AppContext
import site.geniyz.otus.common.models.*
import site.geniyz.otus.common.permissions.AppUserPermissions
import site.geniyz.otus.cor.*

fun ICorChainDsl<AppContext>.searchTypes(title: String) = chain {
    this.title = title
    description = "Добавление ограничений в поисковый запрос согласно правам доступа и др. политикам"
    on { state == AppState.RUNNING }
    worker("Определение типа поиска") {
        objFilterValidated.searchPermissions = setOfNotNull(
            AppSearchPermissions.OWN.takeIf { permissionsChain.contains(AppUserPermissions.OBJ_SEARCH_OWN) },
            AppSearchPermissions.PUBLIC.takeIf { permissionsChain.contains(AppUserPermissions.OBJ_SEARCH_PUBLIC) },
        ).toMutableSet()
    }
}
