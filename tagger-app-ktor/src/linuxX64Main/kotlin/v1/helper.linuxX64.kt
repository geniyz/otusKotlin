package site.geniyz.otus.app.v1

import io.ktor.server.application.*
import site.geniyz.otus.app.AppSettings
import site.geniyz.otus.common.models.AppUserId
import site.geniyz.otus.common.permissions.AppPrincipalModel
import site.geniyz.otus.common.permissions.AppUserGroups

actual fun ApplicationCall.appPrincipal(appSettings: AppSettings): AppPrincipalModel {
    println("in NATIVE ApplicationCall.appPrincipal")
    return AppPrincipalModel(
        id = AppUserId("user-1"),
        fname = "Ivan",
        mname = "Ivanovich",
        lname = "Ivanov",
        groups = setOf(AppUserGroups.TEST, AppUserGroups.USER),
    )
}
