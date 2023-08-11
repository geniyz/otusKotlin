package site.geniyz.otus.app

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import site.geniyz.otus.app.plugins.initAppSettings
import site.geniyz.otus.app.plugins.initPlugins


fun Application.module(appSettings: AppSettings = initAppSettings()) {

    initPlugins(appSettings)

    // val ws = WSController()

    routing {
        get("/") {
            call.respondText("Приложение для работы с метками")
        }

        /* // тут работа с БД и авторизация через JWT — так что оно работае только в JVM ((
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
        */

    }
}

fun main() {
    embeddedServer(io.ktor.server.cio.CIO) {
        module()
    }.start(wait = true)
}
