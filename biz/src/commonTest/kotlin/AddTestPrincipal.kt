package site.geniyz.otus.biz

import site.geniyz.otus.common.AppContext
import site.geniyz.otus.common.models.AppUserId
import site.geniyz.otus.common.permissions.AppPrincipalModel
import site.geniyz.otus.common.permissions.AppUserGroups

fun AppContext.addTestPrincipal(userId: AppUserId = AppUserId("000")) {
    principal = AppPrincipalModel(
        id = userId,
        groups = setOf(
            AppUserGroups.USER,
            AppUserGroups.TEST,
        )
    )
}
