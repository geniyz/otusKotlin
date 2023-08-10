package site.geniyz.otus.auth

import site.geniyz.otus.common.models.AppObjPermissionClient
import site.geniyz.otus.common.permissions.AppPrincipalRelations
import site.geniyz.otus.common.permissions.AppUserPermissions

fun resolveFrontPermissions(
    permissions: Iterable<AppUserPermissions>,
    relations: Iterable<AppPrincipalRelations>,
) = mutableSetOf<AppObjPermissionClient>()
    .apply {
        for (permission in permissions) {
            for (relation in relations) {
                accessTable[permission]?.get(relation)?.let { this@apply.add(it) }
            }
        }
    }
    .toSet()

private val accessTable = mapOf(
    // READ
    AppUserPermissions.OBJ_READ_OWN to mapOf(
        AppPrincipalRelations.OWN to AppObjPermissionClient.READ
    ),
    AppUserPermissions.OBJ_READ_PUBLIC to mapOf(
        AppPrincipalRelations.PUBLIC to AppObjPermissionClient.READ
    ),

    // UPDATE
    AppUserPermissions.OBJ_UPDATE_OWN to mapOf(
        AppPrincipalRelations.OWN to AppObjPermissionClient.UPDATE
    ),

    // DELETE
    AppUserPermissions.OBJ_DELETE_OWN to mapOf(
        AppPrincipalRelations.OWN to AppObjPermissionClient.DELETE
    ),

    /* // SET TAGS
    AppUserPermissions.OBJ_SET_TAGS_OWN to mapOf(
        AppPrincipalRelations.OWN to AppObjPermissionClient.SET_TAGS
    ),
    */
)
