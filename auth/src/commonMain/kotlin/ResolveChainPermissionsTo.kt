package site.geniyz.otus.auth

import site.geniyz.otus.common.permissions.*

fun resolveChainPermissions(
    groups: Iterable<AppUserGroups>,
) = mutableSetOf<AppUserPermissions>()
    .apply {
        addAll(groups.flatMap { groupPermissionsAdmits[it] ?: emptySet() })
        removeAll(groups.flatMap { groupPermissionsDenys[it] ?: emptySet() }.toSet())
    }
    .toSet()

private val groupPermissionsAdmits = mapOf(
    AppUserGroups.USER to setOf(
        AppUserPermissions.OBJ_READ_OWN,
        AppUserPermissions.OBJ_READ_PUBLIC,

        AppUserPermissions.OBJ_SEARCH_OWN,
        AppUserPermissions.OBJ_SEARCH_PUBLIC,

        AppUserPermissions.OBJ_CREATE_OWN,
        AppUserPermissions.OBJ_UPDATE_OWN,
        AppUserPermissions.OBJ_SET_TAGS_OWN,
        AppUserPermissions.OBJ_DELETE_OWN,
    ),
    AppUserGroups.ADMIN to setOf(),
    AppUserGroups.TEST to setOf(),
    AppUserGroups.BANNED to setOf(),
)

private val groupPermissionsDenys = mapOf(
    AppUserGroups.USER to setOf(),
    AppUserGroups.ADMIN to setOf(),
    AppUserGroups.TEST to setOf(),
    AppUserGroups.BANNED to setOf(
        AppUserPermissions.OBJ_UPDATE_OWN,
        AppUserPermissions.OBJ_CREATE_OWN,
    ),
)
