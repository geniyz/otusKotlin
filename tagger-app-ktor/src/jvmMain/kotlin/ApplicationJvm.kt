package site.geniyz.otus.app

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.util.*
import site.geniyz.otus.api.v1.apiV1Mapper
import site.geniyz.otus.app.plugins.initAppSettings
import site.geniyz.otus.app.v1.WSController
import site.geniyz.otus.app.v1.v1Objs
import site.geniyz.otus.app.v1.v1Tags

import site.geniyz.otus.app.module as commonModule

private val clazz = Application::moduleJvm::class.qualifiedName ?: "Application"

@Suppress("unused")
fun Application.moduleJvm(appSettings: AppSettings = initAppSettings()) {

    commonModule(appSettings)

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

fun main(args: Array<String>): Unit = io.ktor.server.cio.EngineMain.main(args)
