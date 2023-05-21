package site.geniyz.otus.app

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

import site.geniyz.otus.api.v1.apiV1Mapper
import site.geniyz.otus.app.v1.*
import site.geniyz.otus.biz.AppProcessor

fun Application.module(processor: AppProcessor = AppProcessor()) {
    routing {
        get("/") {
            call.respondText("Приложение для работы с метками")
        }
        route("v1") {
            install(ContentNegotiation) {
                json(apiV1Mapper)
            }
            v1Objs(processor)
            v1Tags(processor)
        }
    }
}

fun main() {
    embeddedServer(CIO, port = 3333) {
        module()
    }.start(wait = true)
}
