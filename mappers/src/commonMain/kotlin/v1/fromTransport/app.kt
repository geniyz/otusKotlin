package site.geniyz.otus.mappers.v1.fromTransport

import site.geniyz.otus.api.v1.models.*
import site.geniyz.otus.mappers.v1.exceptions.UnknownRequestClass
import site.geniyz.otus.common.AppContext
import site.geniyz.otus.common.models.*

fun AppContext.fromTransport(request: IRequest) = when (request) {
    is ObjCreateRequest   -> fromTransport(request)
    is ObjReadRequest     -> fromTransport(request)
    is ObjUpdateRequest   -> fromTransport(request)
    is ObjDeleteRequest   -> fromTransport(request)
    is ObjSearchRequest   -> fromTransport(request)
    is ObjListTagsRequest -> fromTransport(request)
    is ObjSetTagsRequest  -> fromTransport(request)

    is TagDeleteRequest   -> fromTransport(request)
    is TagSearchRequest   -> fromTransport(request)
    is TagListObjsRequest -> fromTransport(request)

    else -> throw UnknownRequestClass(request::class)
}

fun IRequest?.requestId() = this?.requestId?.let { AppRequestId(it) } ?: AppRequestId.NONE
