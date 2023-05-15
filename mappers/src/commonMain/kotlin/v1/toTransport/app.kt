package site.geniyz.otus.mappers.v1.toTransport

import site.geniyz.otus.api.v1.models.*
import site.geniyz.otus.common.AppContext
import site.geniyz.otus.common.models.*
import site.geniyz.otus.mappers.v1.exceptions.UnknownAppCommand
import kotlinx.datetime.Instant

fun AppContext.toTransport(): IResponse = when (val cmd = command) {
    AppCommand.OBJ_CREATE    -> toTransportObjCreate()
    AppCommand.OBJ_READ      -> toTransportObjRead()
    AppCommand.OBJ_UPDATE    -> toTransportObjUpdate()
    AppCommand.OBJ_DELETE    -> toTransportObjDelete()
    AppCommand.OBJ_SEARCH    -> toTransportObjSearch()
    AppCommand.OBJ_LIST_TAGS -> toTransportObjListTags()
    AppCommand.OBJ_SET_TAGS  -> toTransportObjSetTags()

    AppCommand.TAG_DELETE    -> toTransportTagDelete()
    AppCommand.TAG_SEARCH    -> toTransportTagSearch()
    AppCommand.TAG_LIST_OBJS -> toTransportTagListObjs()

    AppCommand.NONE -> throw UnknownAppCommand(cmd)
}

fun List<AppError>.toTransportErrors(): List<Error> = this
    .map { it.toTransport() }
    .toList()
    .takeIf { it.isNotEmpty() }
    ?: emptyList()

fun AppError.toTransport() = Error(
    code    = code.takeIf { it.isNotBlank() },
    group   = group.takeIf { it.isNotBlank() },
    field   = field.takeIf { it.isNotBlank() },
    message = message.takeIf { it.isNotBlank() },
)

fun AppState.toTransport()= if (this == AppState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR

fun AppRequestId.toTransport()= this.asString().takeIf { it.isNotBlank() }

fun Instant.asString()= toString()
