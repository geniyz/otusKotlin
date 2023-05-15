package site.geniyz.otus.mappers.v1.toTransport

import site.geniyz.otus.api.v1.models.*
import site.geniyz.otus.common.AppContext
import site.geniyz.otus.common.NONE
import site.geniyz.otus.common.models.*
import kotlinx.datetime.Instant


fun AppContext.toTransportTagDelete() = TagDeleteResponse(
    responseType = "tagDelete",
    requestId = requestId.toTransport(),
    result = state.toTransport(),
    errors = errors.toTransportErrors(),
    tag = tagResponse.toTransport(),
)

fun AppContext.toTransportTagSearch() = TagSearchResponse(
    responseType = "tagSearch",
    requestId = requestId.toTransport(),
    result = state.toTransport(),
    errors = errors.toTransportErrors(),
    tags = tagsResponse.toTransport(),
)

fun AppContext.toTransportTagListObjs() = TagListObjsResponse(
    responseType = "tagListObjs",
    requestId = requestId.toTransport(),
    result = state.toTransport(),
    errors = errors.toTransportErrors(),
    tag  = tagResponse.toTransport(),
    objs = objsResponse.toTransport(),
)

fun List<AppTag>.toTransport(): List<TagResponseObject>? = this
    .map { it.toTransport() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun AppTag.toTransport(): TagResponseObject = TagResponseObject(
    name = name.takeIf { it.isNotBlank() },
    code = code.takeIf { it.isNotBlank() },
    id = id.takeIf { it != AppTagId.NONE }?.asString(),
    createdAt = createdAt.takeIf { it != Instant.NONE }?.asString(),
    updatedAt = updatedAt.takeIf { it != Instant.NONE }?.asString(),
    objQty    = objQty
)
