package site.geniyz.otus.app.v1

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString

import site.geniyz.otus.api.v1.apiV1Mapper
import site.geniyz.otus.api.v1.models.*
import site.geniyz.otus.biz.AppProcessor
import site.geniyz.otus.common.AppContext
import site.geniyz.otus.mappers.v1.*
import site.geniyz.otus.mappers.v1.fromTransport.fromTransport
import site.geniyz.otus.mappers.v1.toTransport.*

suspend fun ApplicationCall.objCreate(processor: AppProcessor) { // Создание сущности
    AppContext().let {
        it.fromTransport(apiV1Mapper.decodeFromString<ObjCreateRequest>(receiveText()))
        processor.exec(it)
        respond(apiV1Mapper.encodeToString(it.toTransportObjCreate()))
    }
}

suspend fun ApplicationCall.objRead(processor: AppProcessor) { // Считывание сущности
    AppContext().let {
        it.fromTransport(apiV1Mapper.decodeFromString<ObjReadRequest>(receiveText()))
        processor.exec(it)
        respond(apiV1Mapper.encodeToString(it.toTransportObjRead()))
    }
}

suspend fun ApplicationCall.objUpdate(processor: AppProcessor) { // Изменение сущности
    AppContext().let {
        it.fromTransport(apiV1Mapper.decodeFromString<ObjUpdateRequest>(receiveText()))
        processor.exec(it)
        respond(apiV1Mapper.encodeToString(it.toTransportObjUpdate()))
    }
}

suspend fun ApplicationCall.objDelete(processor: AppProcessor) { // Удаление сущности
    AppContext().let {
        it.fromTransport(apiV1Mapper.decodeFromString<ObjDeleteRequest>(receiveText()))
        processor.exec(it)
        respond(apiV1Mapper.encodeToString(it.toTransportObjDelete()))
    }
}

suspend fun ApplicationCall.objSearch(processor: AppProcessor) { // Поиск/фильтрация сущностей
    AppContext().let {
        it.fromTransport(apiV1Mapper.decodeFromString<ObjSearchRequest>(receiveText()))
        processor.exec(it)
        respond(apiV1Mapper.encodeToString(it.toTransportObjSearch()))
    }
}

suspend fun ApplicationCall.objListTags(processor: AppProcessor) { // Список меток сущности
    AppContext().let {
        it.fromTransport(apiV1Mapper.decodeFromString<ObjListTagsRequest>(receiveText()))
        processor.exec(it)
        respond(apiV1Mapper.encodeToString(it.toTransportObjListTags()))
    }
}

suspend fun ApplicationCall.objSetTags(processor: AppProcessor) { // Изменение перечня меток сущности
    AppContext().let {
        it.fromTransport(apiV1Mapper.decodeFromString<ObjSetTagsRequest>(receiveText()))
        processor.exec(it)
        respond(apiV1Mapper.encodeToString(it.toTransportObjSetTags()))
    }
}
