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

suspend fun ApplicationCall.tagDelete(processor: AppProcessor) { // Удаление метки
    AppContext().let {
        it.fromTransport(apiV1Mapper.decodeFromString<TagDeleteRequest>(receiveText()))
        processor.exec(it)
        respond(apiV1Mapper.encodeToString(it.toTransportTagDelete()))
    }
}

suspend fun ApplicationCall.tagSearch(processor: AppProcessor) { // Поиск/фильтрация меток
    AppContext().let {
        it.fromTransport(apiV1Mapper.decodeFromString<TagSearchRequest>(receiveText()))
        processor.exec(it)
        respond(apiV1Mapper.encodeToString(it.toTransportTagSearch()))
    }
}

suspend fun ApplicationCall.tagObjects(processor: AppProcessor) { // Список сущностей метки
    AppContext().let {
        it.fromTransport(apiV1Mapper.decodeFromString<TagListObjsRequest>(receiveText()))
        processor.exec(it)
        respond(apiV1Mapper.encodeToString(it.toTransportTagListObjs()))
    }
}
