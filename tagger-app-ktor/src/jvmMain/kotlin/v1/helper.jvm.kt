package site.geniyz.otus.app.v1

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import site.geniyz.otus.app.AppSettings
import site.geniyz.otus.app.auth.toModel
import site.geniyz.otus.common.permissions.AppPrincipalModel

actual fun ApplicationCall.appPrincipal(appSettings: AppSettings): AppPrincipalModel {
    println("in JVM! ApplicationCall.appPrincipal")

    return principal<JWTPrincipal>().toModel()
}
