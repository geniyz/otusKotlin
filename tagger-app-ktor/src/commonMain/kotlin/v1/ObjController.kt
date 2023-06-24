package site.geniyz.otus.app.v1

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import site.geniyz.otus.api.logs.mapper.toLog

import site.geniyz.otus.api.v1.apiV1Mapper
import site.geniyz.otus.api.v1.models.*
import site.geniyz.otus.app.AppSettings
import site.geniyz.otus.biz.AppProcessor
import site.geniyz.otus.common.AppContext
import site.geniyz.otus.logging.common.IAppLogWrapper
import site.geniyz.otus.mappers.v1.*
import site.geniyz.otus.mappers.v1.fromTransport.fromTransport
import site.geniyz.otus.mappers.v1.toTransport.*

suspend fun ApplicationCall.objCreate(appSettings: AppSettings, logger: IAppLogWrapper) { // Создание сущности
    logger.doWithLogging {
        AppContext().let {
            it.fromTransport(apiV1Mapper.decodeFromString<ObjCreateRequest>(receiveText()))
            logger.info(msg = "${it.command} request is got", data = it.toLog("objCreate-request"))
            appSettings.processor.exec(it)
            respond(apiV1Mapper.encodeToString(it.toTransportObjCreate()))
        }
    }
}

suspend fun ApplicationCall.objRead(appSettings: AppSettings, logger: IAppLogWrapper) { // Считывание сущности
    logger.doWithLogging {
        AppContext().let {
            it.fromTransport(apiV1Mapper.decodeFromString<ObjReadRequest>(receiveText()))
            logger.info(msg = "${it.command} request is got", data = it.toLog("objRead-request"))
            appSettings.processor.exec(it)
            respond(apiV1Mapper.encodeToString(it.toTransportObjRead()))
        }
    }
}

suspend fun ApplicationCall.objUpdate(appSettings: AppSettings, logger: IAppLogWrapper) { // Изменение сущности
    logger.doWithLogging {
        AppContext().let {
            it.fromTransport(apiV1Mapper.decodeFromString<ObjUpdateRequest>(receiveText()))
            logger.info(msg = "${it.command} request is got", data = it.toLog("objUpdate-request"))
            appSettings.processor.exec(it)
            respond(apiV1Mapper.encodeToString(it.toTransportObjUpdate()))
        }
    }
}

suspend fun ApplicationCall.objDelete(appSettings: AppSettings, logger: IAppLogWrapper) { // Удаление сущности
    logger.doWithLogging {
        AppContext().let {
            it.fromTransport(apiV1Mapper.decodeFromString<ObjDeleteRequest>(receiveText()))
            logger.info(msg = "${it.command} request is got", data = it.toLog("objDelete-request"))
            appSettings.processor.exec(it)
            respond(apiV1Mapper.encodeToString(it.toTransportObjDelete()))
        }
    }
}

suspend fun ApplicationCall.objSearch(appSettings: AppSettings, logger: IAppLogWrapper) { // Поиск/фильтрация сущностей
    logger.doWithLogging {
        AppContext().let {
            it.fromTransport(apiV1Mapper.decodeFromString<ObjSearchRequest>(receiveText()))
            logger.info(msg = "${it.command} request is got", data = it.toLog("objSearch-request"))
            appSettings.processor.exec(it)
            respond(apiV1Mapper.encodeToString(it.toTransportObjSearch()))
        }
    }
}

suspend fun ApplicationCall.objListTags(appSettings: AppSettings, logger: IAppLogWrapper) { // Список меток сущности
    logger.doWithLogging {
        AppContext().let {
            it.fromTransport(apiV1Mapper.decodeFromString<ObjListTagsRequest>(receiveText()))
            logger.info(msg = "${it.command} request is got", data = it.toLog("objListTags-request"))
            appSettings.processor.exec(it)
            respond(apiV1Mapper.encodeToString(it.toTransportObjListTags()))
        }
    }
}

suspend fun ApplicationCall.objSetTags(appSettings: AppSettings, logger: IAppLogWrapper) { // Изменение перечня меток сущности
    logger.doWithLogging {
        AppContext().let {
            it.fromTransport(apiV1Mapper.decodeFromString<ObjSetTagsRequest>(receiveText()))
            logger.info(msg = "${it.command} request is got", data = it.toLog("objSetTags-request"))
            appSettings.processor.exec(it)
            respond(apiV1Mapper.encodeToString(it.toTransportObjSetTags()))
        }
    }
}
