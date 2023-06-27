package site.geniyz.otus.api.logs.mapper

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import site.geniyz.otus.api.logs.models.*
import site.geniyz.otus.common.models.*
import site.geniyz.otus.common.AppContext
import site.geniyz.otus.common.NONE
import site.geniyz.otus.common.helpers.asString

fun AppContext.toLog(logId: String) = CommonLogModel(
    messageTime = Clock.System.now().toString(),
    logId = logId,
    source = "tagger",
    obj = toAppLog(),
    errors = errors.map { it.toLog() },
)

fun AppContext.toAppLog():AppLogModel? {
    val objNone = AppObj()
    val tagNone = AppTag()
    return AppLogModel(
        requestId = requestId.takeIf { it != AppRequestId.NONE }?.asString(),
        requestObj       = objRequest.takeIf { it != objNone }?.toLog(),
        responseObj      = objResponse.takeIf { it != objNone }?.toLog(),
        responseObjs     = objsResponse.takeIf { it.isNotEmpty() }?.filter { it != objNone }?.map { it.toLog() },
        requestObjFilter = objFilterRequest.takeIf { it != AppObjFilter() }?.toLog(),
        requestTag = tagRequest.takeIf { it != tagNone }?.toLog(),

    ).takeIf { it != AppLogModel() }
}

private fun AppObjFilter.toLog() = ObjFilterLog(
    searchString = searchString.takeIf { it.isNotBlank() },
    authorId = ownerId.takeIf { it != AppUserId.NONE }?.asString(),
)

fun AppError.toLog() = ErrorLogModel(
    message = message.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    code = code.takeIf { it.isNotBlank() },
    level = level.name,
)

fun AppObj.toLog() = ObjLog(
    id = id.takeIf { it != AppObjId.NONE }?.asString(),
    name    = name.takeIf { it.isNotBlank() },
    content = content.takeIf { it.isNotBlank() },
    objType = objType.takeIf { it != AppObjType.NONE }?.name,

    authorId  = authorId.takeIf { it != AppUserId.NONE }?.asString(),
    createdAt = createdAt.takeIf { it != Instant.NONE }?.asString(),
    updatedAt = updatedAt.takeIf { it != Instant.NONE }?.asString(),
)

fun AppTag.toLog() = TagLog(
    id = id.takeIf { it != AppTagId.NONE }?.asString(),
    code    = code.takeIf { it.isNotBlank() },
    name    = name.takeIf { it.isNotBlank() },

    createdAt = createdAt.takeIf { it != Instant.NONE }?.asString(),
    updatedAt = updatedAt.takeIf { it != Instant.NONE }?.asString(),
)
