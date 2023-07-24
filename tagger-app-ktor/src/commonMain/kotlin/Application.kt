package site.geniyz.otus.app

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.util.*

import site.geniyz.otus.api.v1.apiV1Mapper
import site.geniyz.otus.app.plugins.initAppSettings
import site.geniyz.otus.app.plugins.initPlugins
import site.geniyz.otus.app.v1.*

fun Application.module(appSettings: AppSettings = initAppSettings()) {

    initPlugins(appSettings)

    val ws = WSController()

    routing {
        get("/") {
            call.respondText("Приложение для работы с метками")
        }
        route("v1") {
            pluginRegistry.getOrNull(AttributeKey("ContentNegotiation"))?:install(ContentNegotiation) {
                json(apiV1Mapper)
            }
            v1Objs(appSettings)
            v1Tags(appSettings)
        }

        webSocket("/ws/v1") {
            ws.handle(this, appSettings)
        }
    }
}

fun main() {
    embeddedServer(CIO) {
        module()
    }.start(wait = true)
}
