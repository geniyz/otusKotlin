package site.geniyz.otus.auth

import site.geniyz.otus.common.models.AppCommand
import site.geniyz.otus.common.permissions.*

fun checkPermitted(
    command: AppCommand,
    relations: Iterable<AppPrincipalRelations>,
    permissions: Iterable<AppUserPermissions>,
) =
    relations.asSequence().flatMap { relation ->
        permissions.map { permission ->
            AccessTableConditions(
                command = command,
                permission = permission,
                relation = relation,
            )
        }
    }.any {
        accessTable[it] != null
    }

private data class AccessTableConditions(
    val command: AppCommand,
    val permission: AppUserPermissions,
    val relation: AppPrincipalRelations
)

private val accessTable = mapOf(
    // Create
    AccessTableConditions(
        command    = AppCommand.OBJ_CREATE,
        permission = AppUserPermissions.OBJ_CREATE_OWN,
        relation   = AppPrincipalRelations.NEW,
    ) to true,

    // Read
    AccessTableConditions(
        command    = AppCommand.OBJ_READ,
        permission = AppUserPermissions.OBJ_READ_OWN,
        relation   = AppPrincipalRelations.OWN,
    ) to true,
    AccessTableConditions(
        command    = AppCommand.OBJ_READ,
        permission = AppUserPermissions.OBJ_READ_PUBLIC,
        relation   = AppPrincipalRelations.PUBLIC,
    ) to true,

    // Update
    AccessTableConditions(
        command    = AppCommand.OBJ_UPDATE,
        permission = AppUserPermissions.OBJ_UPDATE_OWN,
        relation   = AppPrincipalRelations.OWN,
    ) to true,

    // Delete
    AccessTableConditions(
        command    = AppCommand.OBJ_DELETE,
        permission = AppUserPermissions.OBJ_DELETE_OWN,
        relation   = AppPrincipalRelations.OWN,
    ) to true,

    // Set tags
    AccessTableConditions(
        command    = AppCommand.OBJ_SET_TAGS,
        permission = AppUserPermissions.OBJ_SET_TAGS_OWN,
        relation   = AppPrincipalRelations.OWN,
    ) to true,
)
