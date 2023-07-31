package site.geniyz.otus.mappers.v1.fromTransport

import site.geniyz.otus.api.v1.models.*
import site.geniyz.otus.common.AppContext
import site.geniyz.otus.common.models.*
import site.geniyz.otus.common.stubs.AppStubs

private fun String?.toObjId() = this?.let { AppObjId(it) } ?: AppObjId.NONE
private fun String?.toObjWithId() = AppObj(id = this.toObjId())

private fun ObjDebug?.transportToWorkMode(): AppWorkMode = when (this?.mode) {
    RequestDebugMode.PROD -> AppWorkMode.PROD
    RequestDebugMode.TEST -> AppWorkMode.TEST
    RequestDebugMode.STUB -> AppWorkMode.STUB
    null -> AppWorkMode.PROD
}

private fun ObjDebug?.transportToStubCase(): AppStubs = when (this?.stub) {
    ObjRequestDebugStubs.SUCCESS           -> AppStubs.SUCCESS
    ObjRequestDebugStubs.NOT_FOUND         -> AppStubs.NOT_FOUND
    ObjRequestDebugStubs.BAD_ID            -> AppStubs.BAD_ID
    ObjRequestDebugStubs.BAD_NAME          -> AppStubs.BAD_NAME
    ObjRequestDebugStubs.BAD_TYPE          -> AppStubs.BAD_TYPE
    ObjRequestDebugStubs.BAD_CONTENT       -> AppStubs.BAD_CONTENT
    ObjRequestDebugStubs.CANNOT_DELETE     -> AppStubs.CANNOT_DELETE
    ObjRequestDebugStubs.BAD_SEARCH_STRING -> AppStubs.BAD_SEARCH_STRING
    null -> AppStubs.NONE
}


fun AppContext.fromTransport(request: ObjCreateRequest) {
    command = AppCommand.OBJ_CREATE
    requestId = request.requestId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
    objRequest = request.obj?.toInternal() ?: AppObj()
}

fun AppContext.fromTransport(request: ObjReadRequest) {
    command = AppCommand.OBJ_READ
    requestId = request.requestId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
    objRequest = request.obj?.id.toObjWithId()
}

fun AppContext.fromTransport(request: ObjUpdateRequest) {
    command = AppCommand.OBJ_UPDATE
    requestId = request.requestId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
    objRequest = request.obj?.toInternal() ?: AppObj()
}

fun AppContext.fromTransport(request: ObjDeleteRequest) {
    command = AppCommand.OBJ_DELETE
    requestId = request.requestId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
    objRequest = request.obj?.id.toObjWithId()
}

fun AppContext.fromTransport(request: ObjSearchRequest) {
    command = AppCommand.OBJ_SEARCH
    requestId = request.requestId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
    objFilterRequest = request.objFilter.toInternal()
}

fun AppContext.fromTransport(request: ObjListTagsRequest) {
    command = AppCommand.OBJ_LIST_TAGS
    requestId = request.requestId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
    objRequest = request.obj?.id.toObjWithId()
}

fun AppContext.fromTransport(request: ObjSetTagsRequest) {
    command = AppCommand.OBJ_SET_TAGS
    requestId = request.requestId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
    objRequest = request.obj?.id.toObjWithId()
    tagsRequest = request.obj?.tags?.map { AppTag(code = it) }?.toMutableList() ?: mutableListOf()
}

private fun ObjSearchFilter?.toInternal() = AppObjFilter(
    searchString = this?.searchString ?: ""
)

private fun ObjCreateObject.toInternal() = AppObj(
    name = this.name ?: "",
    content = this.content ?: "",
    objType = this.objType.fromTransport(),
)

private fun ObjUpdateObject.toInternal() = AppObj(
    id = this.id.toObjId(),
    name = this.name ?: "",
    content = this.content ?: "",
    objType = this.objType.fromTransport(),
    lock = this.lock.toAppLock(),
)

private fun ObjDeleteObject?.toInternal(): AppObj = if (this != null) {
    AppObj(
        id = id.toObjId(),
        lock = lock.toAppLock(),
    )
} else {
    AppObj.NONE
}

private fun ObjType?.fromTransport(): AppObjType = when (this) {
    ObjType.TEXT   -> AppObjType.TEXT
    ObjType.HREF   -> AppObjType.HREF
    ObjType.BASE64 -> AppObjType.BASE64
    null -> AppObjType.NONE
}
