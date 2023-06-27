package site.geniyz.otus.mappers.v1.toTransport

import site.geniyz.otus.api.v1.models.*
import site.geniyz.otus.common.AppContext
import site.geniyz.otus.common.models.*

import kotlinx.datetime.Instant
import site.geniyz.otus.common.NONE
import site.geniyz.otus.common.helpers.asString

fun AppContext.toTransportObjCreate() = ObjCreateResponse(
    responseType = "objCreate",
    requestId = requestId.toTransport(),
    result = state.toTransport(),
    errors = errors.toTransportErrors(),
    obj = objResponse.toTransport(),
)

fun AppContext.toTransportObjRead() = ObjReadResponse(
    responseType = "objRead",
    requestId = requestId.toTransport(),
    result = state.toTransport(),
    errors = errors.toTransportErrors(),
    obj = objResponse.toTransport(),
)

fun AppContext.toTransportObjUpdate() = ObjUpdateResponse(
    responseType = "objUpdate",
    requestId = requestId.toTransport(),
    result = state.toTransport(),
    errors = errors.toTransportErrors(),
    obj = objResponse.toTransport(),
)

fun AppContext.toTransportObjDelete() = ObjDeleteResponse(
    responseType = "objDelete",
    requestId = requestId.toTransport(),
    result = state.toTransport(),
    errors = errors.toTransportErrors(),
    obj = objResponse.toTransport(),
)

fun AppContext.toTransportObjSearch() = ObjSearchResponse(
    responseType = "objSearch",
    requestId = requestId.toTransport(),
    result = state.toTransport(),
    errors = errors.toTransportErrors(),
    objs = objsResponse.toTransport(),
)

fun AppContext.toTransportObjListTags() = ObjListTagsResponse(
    responseType = "objListTags",
    requestId = requestId.toTransport(),
    result = state.toTransport(),
    errors = errors.toTransportErrors(),
    obj  = objResponse.toTransport(),
    tags = tagsResponse.toTransport(),
)

fun AppContext.toTransportObjSetTags() = ObjSetTagsResponse(
    responseType = "objListTags",
    requestId = requestId.toTransport(),
    result = state.toTransport(),
    errors = errors.toTransportErrors(),
    obj  = objResponse.toTransport(),
    tags = tagsResponse.toTransport(),
)

fun AppContext.toTransportInit() = ObjInitResponse(
    responseType = "init",
    requestId = requestId.toTransport(),
    result = if (errors.isEmpty()) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
)

fun List<AppObj>.toTransport(): List<ObjResponseObject>? = this
    .map { it.toTransport() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun AppObj.toTransport(): ObjResponseObject = ObjResponseObject(
    name = name.takeIf { it.isNotBlank() },
    content = content.takeIf { it.isNotBlank() },
    objType = objType.toTransport(),
    id = id.takeIf { it != AppObjId.NONE }?.asString(),
    authorId = authorId.takeIf { it != AppUserId.NONE }?.asString(),
    createdAt = createdAt.takeIf { it != Instant.NONE }?.asString(),
    updatedAt = updatedAt.takeIf { it != Instant.NONE }?.asString(),
)

private fun AppObjType.toTransport(): ObjType? = when (this) {
    AppObjType.TEXT   -> ObjType.TEXT
    AppObjType.HREF   -> ObjType.HREF
    AppObjType.BASE64 -> ObjType.BASE64
    AppObjType.NONE   -> null
}
