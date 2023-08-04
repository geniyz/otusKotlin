package site.geniyz.otus.app.auth

import io.ktor.server.auth.jwt.*
import site.geniyz.otus.app.auth.AuthConfig.Companion.ID_CLAIM
import site.geniyz.otus.app.auth.AuthConfig.Companion.F_NAME_CLAIM
import site.geniyz.otus.app.auth.AuthConfig.Companion.L_NAME_CLAIM
import site.geniyz.otus.app.auth.AuthConfig.Companion.M_NAME_CLAIM
import site.geniyz.otus.app.auth.AuthConfig.Companion.GROUPS_CLAIM
import site.geniyz.otus.common.permissions.AppPrincipalModel
import site.geniyz.otus.common.permissions.AppUserGroups
import site.geniyz.otus.common.models.AppUserId

fun JWTPrincipal?.toModel() = this?.run {
    AppPrincipalModel(
        id = payload.getClaim(ID_CLAIM).asString()?.let { AppUserId(it) } ?: AppUserId.NONE,
        fname = payload.getClaim(F_NAME_CLAIM).asString() ?: "",
        mname = payload.getClaim(M_NAME_CLAIM).asString() ?: "",
        lname = payload.getClaim(L_NAME_CLAIM).asString() ?: "",
        groups = payload
            .getClaim(GROUPS_CLAIM)
            ?.asList(String::class.java)
            ?.mapNotNull {
                when(it) {
                    "USER" -> AppUserGroups.USER
                    else -> null
                }
            }?.toSet() ?: emptySet()
    )
} ?: AppPrincipalModel.NONE
