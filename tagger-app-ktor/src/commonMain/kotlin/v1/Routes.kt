package site.geniyz.otus.app.v1

import io.ktor.server.application.*
import io.ktor.server.routing.*
import site.geniyz.otus.app.AppSettings

fun Route.v1Objs(appSettings: AppSettings) {
    val logger = appSettings.loggerProvider.logger(Route::v1Objs)
    route("obj") {
        post("create") {
            call.objCreate(appSettings, logger)
        }
        post("read") {
            call.objRead(appSettings, logger)
        }
        post("update") {
            call.objUpdate(appSettings, logger)
        }
        post("delete") {
            call.objDelete(appSettings, logger)
        }
        post("search") {
            call.objSearch(appSettings, logger)
        }
        post("listTags") {
            call.objListTags(appSettings, logger)
        }
        post("setTags") {
            call.objSetTags(appSettings, logger)
        }
    }
}

fun Route.v1Tags(appSettings: AppSettings) {
    val logger = appSettings.loggerProvider.logger(Route::v1Tags)
    route("tag") {
        post("delete") {
            call.tagDelete(appSettings, logger)
        }
        post("search") {
            call.tagSearch(appSettings, logger)
        }
        post("objects") {
            call.tagObjects(appSettings, logger)
        }
    }
}
