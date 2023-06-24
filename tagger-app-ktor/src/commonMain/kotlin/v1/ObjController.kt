package site.geniyz.otus.app.v1

import io.ktor.server.application.*

import site.geniyz.otus.api.v1.models.*
import site.geniyz.otus.app.AppSettings
import site.geniyz.otus.common.models.AppCommand
import site.geniyz.otus.logging.common.IAppLogWrapper

suspend fun ApplicationCall.objCreate(appSettings: AppSettings, logger: IAppLogWrapper) = // Создание сущности
    processV1<ObjCreateRequest, ObjCreateResponse>(appSettings, logger, "objCreate", AppCommand.OBJ_CREATE)

suspend fun ApplicationCall.objRead(appSettings: AppSettings, logger: IAppLogWrapper) = // Считывание сущности
    processV1<ObjReadRequest, ObjReadResponse>(appSettings, logger, "objRead", AppCommand.OBJ_READ)

suspend fun ApplicationCall.objUpdate(appSettings: AppSettings, logger: IAppLogWrapper) = // Изменение сущности
    processV1<ObjUpdateRequest, ObjUpdateResponse>(appSettings, logger, "objUpdate", AppCommand.OBJ_UPDATE)

suspend fun ApplicationCall.objDelete(appSettings: AppSettings, logger: IAppLogWrapper) = // Удаление сущности
    processV1<ObjDeleteRequest, ObjDeleteResponse>(appSettings, logger, "objDelete", AppCommand.OBJ_DELETE)

suspend fun ApplicationCall.objSearch(appSettings: AppSettings, logger: IAppLogWrapper) = // Поиск/фильтрация сущностей
    processV1<ObjSearchRequest, ObjSearchResponse>(appSettings, logger, "objSearch", AppCommand.OBJ_SEARCH)

suspend fun ApplicationCall.objListTags(appSettings: AppSettings, logger: IAppLogWrapper) = // Список меток сущности
    processV1<ObjListTagsRequest, ObjListTagsResponse>(appSettings, logger, "objListTags", AppCommand.OBJ_LIST_TAGS)

suspend fun ApplicationCall.objSetTags(appSettings: AppSettings, logger: IAppLogWrapper) = // Изменение перечня меток сущности
    processV1<ObjSetTagsRequest, ObjSetTagsResponse>(appSettings, logger, "objSetTags", AppCommand.OBJ_SET_TAGS)
