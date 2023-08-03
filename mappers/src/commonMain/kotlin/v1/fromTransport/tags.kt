package site.geniyz.otus.mappers.v1.fromTransport

import site.geniyz.otus.api.v1.models.*
import site.geniyz.otus.common.AppContext
import site.geniyz.otus.common.models.*
import site.geniyz.otus.common.stubs.AppStubs

private fun String?.toTagId() = this?.let { AppTagId(it) } ?: AppTagId.NONE
private fun String?.toTagWithId(lock: String? = "") = AppTag(id = this.toTagId(), lock = lock.toAppLock())

private fun TagDebug?.transportToWorkMode(): AppWorkMode = when (this?.mode) {
    RequestDebugMode.PROD -> AppWorkMode.PROD
    RequestDebugMode.TEST -> AppWorkMode.TEST
    RequestDebugMode.STUB -> AppWorkMode.STUB
    null -> AppWorkMode.PROD
}

private fun TagDebug?.transportToStubCase(): AppStubs = when (this?.stub) {
    TagRequestDebugStubs.SUCCESS           -> AppStubs.SUCCESS
    TagRequestDebugStubs.NOT_FOUND         -> AppStubs.NOT_FOUND
    TagRequestDebugStubs.BAD_CODE          -> AppStubs.BAD_CODE
    TagRequestDebugStubs.BAD_NAME          -> AppStubs.BAD_NAME
    TagRequestDebugStubs.CANNOT_DELETE     -> AppStubs.CANNOT_DELETE
    TagRequestDebugStubs.BAD_SEARCH_STRING -> AppStubs.BAD_SEARCH_STRING
    null -> AppStubs.NONE
}

fun AppContext.fromTransport(request: TagDeleteRequest) {
    command = AppCommand.TAG_DELETE
    requestId = request.requestId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
    tagRequest = request.tag?.id.toTagWithId( lock= request.tag?.lock )
}

fun AppContext.fromTransport(request: TagSearchRequest) {
    command = AppCommand.TAG_SEARCH
    requestId = request.requestId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
    tagFilterRequest = request.tagFilter.toInternal()
}

fun AppContext.fromTransport(request: TagListObjsRequest) {
    command = AppCommand.TAG_LIST_OBJS
    requestId = request.requestId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
    tagRequest = request.tag?.id.toTagWithId()
}

private fun TagSearchFilter?.toInternal() = AppTagFilter(
    searchString = this?.searchString ?: ""
)

private fun TagDeleteObject?.toInternal(): AppTag = if (this != null) {
    AppTag(
        id = id.toTagId(),
        lock = lock.toAppLock(),
    )
} else {
    AppTag.NONE
}
