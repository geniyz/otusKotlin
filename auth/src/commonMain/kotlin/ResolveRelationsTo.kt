package site.geniyz.otus.auth

import site.geniyz.otus.common.models.*
import site.geniyz.otus.common.permissions.*

fun AppObj.resolveRelationsTo(principal: AppPrincipalModel): Set<AppPrincipalRelations> = setOfNotNull(
    AppPrincipalRelations.NONE,
    AppPrincipalRelations.NEW.takeIf { AppObjId.NONE == id },      // новый объект — у него ещё нет идентификатора
    AppPrincipalRelations.OWN.takeIf { principal.id == authorId }, // пользователь — автор объекта
    // AppPrincipalRelations.MODERATABLE.takeIf { visibility != AppVisibility.VISIBLE_TO_OWNER },
    // AppPrincipalRelations.PUBLIC.takeIf { visibility == AppVisibility.VISIBLE_PUBLIC },
)
