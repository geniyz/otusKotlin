package site.geniyz.otus.common

import kotlinx.datetime.Instant

import site.geniyz.otus.common.models.*
import site.geniyz.otus.common.permissions.*
import site.geniyz.otus.common.stubs.AppStubs
import site.geniyz.otus.common.repo.IRepository

data class AppContext(
    var command: AppCommand = AppCommand.NONE,
    var state:   AppState   = AppState.NONE,
    val errors:  MutableList<AppError> = mutableListOf(),

    var settings: CorSettings = CorSettings.NONE,

    var workMode: AppWorkMode = AppWorkMode.PROD,
    var stubCase: AppStubs    = AppStubs.NONE,

    var requestId: AppRequestId = AppRequestId.NONE,
    var timeStart: Instant      = Instant.NONE,

    var objRequest:       AppObj              = AppObj(),         // запрос - объект
    var objFilterRequest: AppObjFilter        = AppObjFilter(),   // поисковый запрос при фильтрации объектов
    var objResponse:      AppObj              = AppObj(),         // ответ - объект
    var objsResponse:     MutableList<AppObj> = mutableListOf(),  // ответ - набор объектов

    var tagRequest:       AppTag              = AppTag(),          // запрос - метка
    var tagsRequest:      MutableList<AppTag> = mutableListOf(),   // запрос - набор меток (при назначении меток объекту, например)
    var tagFilterRequest: AppTagFilter        = AppTagFilter(),    // поисковый запрос при фильтрации меток
    var tagResponse:      AppTag              = AppTag(),          // ответ - метка
    var tagsResponse:     MutableList<AppTag> = mutableListOf(),   // ответ - набор меток

    var objValidating:       AppObj              = AppObj(),
    var objFilterValidating: AppObjFilter        = AppObjFilter(),

    var objValidated:        AppObj              = AppObj(),
    var objFilterValidated:  AppObjFilter        = AppObjFilter(),

    var tagValidating:       AppTag              = AppTag(),
    var tagsValidating:      MutableList<AppTag> = mutableListOf(),
    var tagFilterValidating: AppTagFilter        = AppTagFilter(),

    var tagValidated:        AppTag              = AppTag(),
    var tagsValidated:       MutableList<AppTag> = mutableListOf(),
    var tagFilterValidated:  AppTagFilter        = AppTagFilter(),

    var repo:                IRepository         = IRepository.NONE,

    var objRepoPrepare:      AppObj              = AppObj(),
    var objRepoRead:         AppObj              = AppObj(),
    var objRepoDone:         AppObj              = AppObj(),
    var objsRepoDone:        MutableList<AppObj> = mutableListOf(),

    var tagRepoPrepare:      AppTag              = AppTag(),
    var tagRepoRead:         AppTag              = AppTag(),
    var tagRepoDone:         AppTag              = AppTag(),
    var tagsRepoDone:        MutableList<AppTag> = mutableListOf(),


    var principal:           AppPrincipalModel = AppPrincipalModel.NONE,
    val permissionsChain:    MutableSet<AppUserPermissions> = mutableSetOf(),
    var permitted:           Boolean = false,

    )
