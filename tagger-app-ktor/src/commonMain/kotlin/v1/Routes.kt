package site.geniyz.otus.app.v1

import io.ktor.server.application.*
import io.ktor.server.routing.*

import site.geniyz.otus.biz.AppProcessor

fun Route.v1Objs(processor: AppProcessor) {
    route("obj") {
        post("create") {
            call.objCreate(processor)
        }
        post("read") {
            call.objRead(processor)
        }
        post("update") {
            call.objUpdate(processor)
        }
        post("delete") {
            call.objDelete(processor)
        }
        post("search") {
            call.objSearch(processor)
        }
        post("listTags") {
            call.objListTags(processor)
        }
        post("setTags") {
            call.objSetTags(processor)
        }
    }
}

fun Route.v1Tags(processor: AppProcessor) {
    route("tag") {
        post("delete") {
            call.tagDelete(processor)
        }
        post("search") {
            call.tagSearch(processor)
        }
        post("objects") {
            call.tagObjects(processor)
        }
    }
}
