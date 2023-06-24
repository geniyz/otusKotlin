package site.geniyz.otus.app.v1

import io.ktor.server.application.*
import site.geniyz.otus.api.v1.models.*
import site.geniyz.otus.app.AppSettings
import site.geniyz.otus.logging.common.IAppLogWrapper
import site.geniyz.otus.common.models.AppCommand

suspend fun ApplicationCall.tagDelete(appSettings: AppSettings, logger: IAppLogWrapper) = // Удаление метки
    processV1<TagDeleteRequest, TagDeleteResponse>(appSettings, logger, "tagDelete", AppCommand.TAG_DELETE)

suspend fun ApplicationCall.tagSearch(appSettings: AppSettings, logger: IAppLogWrapper) = // Поиск/фильтрация меток
    processV1<TagSearchRequest, TagSearchResponse>(appSettings, logger, "tagSearch", AppCommand.TAG_SEARCH)

suspend fun ApplicationCall.tagObjects(appSettings: AppSettings, logger: IAppLogWrapper) = // Список сущностей метки
    processV1<TagListObjsRequest, TagListObjsResponse>(appSettings, logger, "tagListObjs", AppCommand.TAG_LIST_OBJS)
