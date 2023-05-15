package site.geniyz.otus.common.helpers

import site.geniyz.otus.common.AppContext
import site.geniyz.otus.common.models.AppCommand

fun AppContext.isUpdatableCommand() =
    this.command in listOf(
        AppCommand.OBJ_CREATE, AppCommand.OBJ_UPDATE, AppCommand.OBJ_DELETE,
        AppCommand.OBJ_SET_TAGS, AppCommand.OBJ_DELETE, AppCommand.OBJ_DELETE,
        AppCommand.TAG_DELETE,
    )
