package site.geniyz.otus.common

import kotlinx.datetime.Instant

import site.geniyz.otus.common.models.*
import site.geniyz.otus.common.stubs.AppStubs

data class AppContext(
    var command: AppCommand = AppCommand.NONE,
    var state:   AppState   = AppState.NONE,
    val errors:  MutableList<AppError> = mutableListOf(),

    var workMode: AppWorkMode = AppWorkMode.PROD,
    var stubCase: AppStubs    = AppStubs.NONE,

    var requestId: AppRequestId = AppRequestId.NONE,
    var timeStart: Instant      = Instant.NONE,

    var objRequest:       AppObj              = AppObj(),
    var objFilterRequest: AppObjFilter        = AppObjFilter(),
    var objResponse:      AppObj              = AppObj(),
    var objsResponse:     MutableList<AppObj> = mutableListOf(),

    var tagRequest:       AppTag              = AppTag(),
    var tagFilterRequest: AppTagFilter        = AppTagFilter(),
    var tagResponse:      AppTag              = AppTag(),
    var tagsResponse:     MutableList<AppTag> = mutableListOf(),
)
