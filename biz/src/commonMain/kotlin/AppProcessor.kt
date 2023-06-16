package site.geniyz.otus.biz

import site.geniyz.otus.common.AppContext
import site.geniyz.otus.common.models.*
import site.geniyz.otus.stubs.*

class AppProcessor {
    suspend fun exec(ctx: AppContext) {
        require(ctx.workMode == AppWorkMode.STUB) { // TODO: Rewrite temporary stub solution with BIZ
            "На данный момент приложение работает только в STUB-режиме."
        }

        if(ctx.command.name.startsWith("OBJ_")) { // Объекты
            when (ctx.command) {

                AppCommand.OBJ_CREATE -> {
                    ctx.objResponse = AppStubObjs.getText()
                }

                AppCommand.OBJ_READ -> {
                    ctx.objResponse = AppStubObjs.getText()
                }

                AppCommand.OBJ_UPDATE -> {
                    ctx.objResponse = AppStubObjs.getText()
                }

                AppCommand.OBJ_DELETE -> {
                    ctx.objResponse = AppStubObjs.getText()
                }

                AppCommand.OBJ_SEARCH -> {
                    ctx.objsResponse.addAll(AppStubObjs.prepareSearchList("Объектъ"))
                }

                AppCommand.OBJ_LIST_TAGS -> {
                    ctx.objResponse = AppStubObjs.getText()
                    ctx.tagsResponse.addAll(AppStubTags.prepareSearchList("Метка"))
                }

                AppCommand.OBJ_SET_TAGS -> {
                    ctx.objResponse = AppStubObjs.getText()
                    ctx.tagsResponse.addAll(AppStubTags.prepareSearchList("Метка"))
                }

                else -> {
                    ctx.objResponse = AppStubObjs.getText()
                }
            }
        }

        if(ctx.command.name.startsWith("TAG_")) { // Метки
            when (ctx.command) {

                AppCommand.TAG_DELETE -> {
                    ctx.tagResponse = AppStubTags.get()
                }

                AppCommand.TAG_SEARCH -> {
                    ctx.tagsResponse.addAll(AppStubTags.prepareSearchList("Метка"))
                }

                AppCommand.TAG_LIST_OBJS -> {
                    ctx.tagResponse = AppStubTags.get()
                    ctx.objsResponse.addAll(AppStubObjs.prepareSearchList("Объектъ"))
                }

                else -> {
                    ctx.tagResponse = AppStubTags.get()
                }
            }
        }

    }
}
