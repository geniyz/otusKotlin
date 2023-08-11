package site.geniyz.otus.app.v1

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import site.geniyz.otus.api.v1.apiV1Mapper
import site.geniyz.otus.api.v1.models.*
import site.geniyz.otus.app.AppSettings
import site.geniyz.otus.common.models.*
import site.geniyz.otus.common.permissions.*
import site.geniyz.otus.logging.common.IAppLogWrapper
import site.geniyz.otus.mappers.v1.fromTransport.fromTransport
import site.geniyz.otus.mappers.v1.toTransport.toTransport

suspend inline fun <reified Q : IRequest, @Suppress("unused") reified R : IResponse> ApplicationCall.processV1(
    appSettings: AppSettings,
    logger: IAppLogWrapper,
    logId: String,
    command: AppCommand,
) {
    appSettings.processor.process(logger, logId, command,
        {ctx ->
            ctx.principal = request.call.appPrincipal(appSettings)
            ctx.fromTransport( apiV1Mapper.decodeFromString<Q>(receiveText()) )
        },
        { ctx ->
            respondText(apiV1Mapper.encodeToString(ctx.toTransport()), ContentType.Application.Json)
        })
}

expect fun ApplicationCall.appPrincipal(appSettings: AppSettings): AppPrincipalModel
