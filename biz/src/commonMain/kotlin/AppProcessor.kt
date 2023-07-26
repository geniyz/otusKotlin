package site.geniyz.otus.biz

import kotlinx.datetime.Clock

import site.geniyz.otus.common.AppContext
import site.geniyz.otus.common.models.*

import site.geniyz.otus.logging.common.*

import site.geniyz.otus.api.logs.mapper.toLog
import site.geniyz.otus.common.CorSettings

import site.geniyz.otus.common.helpers.asAppError
import site.geniyz.otus.common.helpers.fail

class AppProcessor(val settings: CorSettings) {
    suspend fun exec(ctx: AppContext){
            AnyBusinessChain.exec(ctx)
            when (ctx.command.name.substringBefore("_")) {
                "OBJ" -> ObjBusinessChain.exec(ctx)
                "TAG" -> TagBusinessChain.exec(ctx)
                else  -> OtherBusinessChain.exec(ctx)
            }
    }

    suspend fun <T> process(
        logger: IAppLogWrapper,
        logId: String,
        command: AppCommand,
        fromTransport: suspend (AppContext) -> Unit,
        sendResponse: suspend (AppContext) -> T): T {

        val ctx = AppContext(
            timeStart = Clock.System.now(),
        ).copy(settings = this@AppProcessor.settings)

        var realCommand = command

        return try {
            logger.doWithLogging(id = logId) {
                fromTransport(ctx)
                realCommand = ctx.command

                logger.info(
                    msg = "$realCommand request is got",
                    data = ctx.toLog("${logId}-got")
                )
                exec(ctx)
                logger.info(
                    msg = "$realCommand request is handled",
                    data = ctx.toLog("${logId}-handled")
                )
                sendResponse(ctx)
            }
        } catch (e: Throwable) {
            logger.doWithLogging(id = "${logId}-failure") {
                logger.error(msg = "$realCommand handling failed")

                ctx.command = realCommand
                ctx.fail(e.asAppError())
                exec(ctx)
                sendResponse(ctx)
            }
        }
    }

}
