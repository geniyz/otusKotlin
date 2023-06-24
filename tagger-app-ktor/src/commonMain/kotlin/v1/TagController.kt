package site.geniyz.otus.app.v1

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString

import site.geniyz.otus.api.v1.apiV1Mapper
import site.geniyz.otus.api.v1.models.*
import site.geniyz.otus.app.AppSettings
import site.geniyz.otus.common.AppContext
import site.geniyz.otus.logging.common.IAppLogWrapper
import site.geniyz.otus.mappers.v1.fromTransport.fromTransport
import site.geniyz.otus.mappers.v1.toTransport.*
import site.geniyz.otus.api.logs.mapper.toLog

suspend fun ApplicationCall.tagDelete(appSettings: AppSettings, logger: IAppLogWrapper) { // Удаление метки
    logger.doWithLogging {
        AppContext().let {
            it.fromTransport(apiV1Mapper.decodeFromString<TagDeleteRequest>(receiveText()))
            logger.info(msg = "${it.command} request is got", data = it.toLog("tagDelete-request") )
            appSettings.processor.exec(it)
            respond(apiV1Mapper.encodeToString(it.toTransportTagDelete()))
        }
    }
}

suspend fun ApplicationCall.tagSearch(appSettings: AppSettings, logger: IAppLogWrapper) { // Поиск/фильтрация меток
    logger.doWithLogging {
        AppContext().let {
            it.fromTransport(apiV1Mapper.decodeFromString<TagSearchRequest>(receiveText()))
            logger.info(msg = "${it.command} request is got", data = it.toLog("tagSearch-request"))
            appSettings.processor.exec(it)
            respond(apiV1Mapper.encodeToString(it.toTransportTagSearch()))
        }
    }
}

suspend fun ApplicationCall.tagObjects(appSettings: AppSettings, logger: IAppLogWrapper) { // Список сущностей метки
    logger.doWithLogging {
        AppContext().let {
            it.fromTransport(apiV1Mapper.decodeFromString<TagListObjsRequest>(receiveText()))
            logger.info(msg = "${it.command} request is got", data = it.toLog("tagObjects-request"))
            appSettings.processor.exec(it)
            respond(apiV1Mapper.encodeToString(it.toTransportTagListObjs()))
        }
    }
}
