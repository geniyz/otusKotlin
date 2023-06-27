package site.geniyz.otus.app

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import org.slf4j.event.Level
import site.geniyz.otus.api.v1.apiV1Mapper
import site.geniyz.otus.app.plugins.getLoggerProviderConf
import site.geniyz.otus.app.plugins.initAppSettings
import site.geniyz.otus.app.v1.WSController
import site.geniyz.otus.app.v1.v1Objs
import site.geniyz.otus.app.v1.v1Tags
import site.geniyz.otus.biz.AppProcessor
import site.geniyz.otus.logging.jvm.AppLogWrapperLogback

private val clazz = Application::moduleJvm::class.qualifiedName ?: "Application"

@Suppress("unused")
fun Application.moduleJvm(appSettings: AppSettings = initAppSettings()) {

    val lgr = appSettings.loggerProvider as? AppLogWrapperLogback
    install(CallLogging) {
        level = Level.INFO
        lgr?.logger?.also { logger = it }
    }

    install(WebSockets)

    val ws = WSController()

    routing {
        get("/") {
            call.respondText("Приложение для работы с метками")
        }
        route("v1") {
            install(ContentNegotiation) {
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
