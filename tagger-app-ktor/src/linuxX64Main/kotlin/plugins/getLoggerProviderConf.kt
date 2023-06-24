package site.geniyz.otus.app.plugins

import io.ktor.server.application.*
import site.geniyz.otus.logging.common.AppLoggerProvider
import site.geniyz.otus.logging.kermit.appLoggerKermit

actual fun Application.getLoggerProviderConf(): AppLoggerProvider = AppLoggerProvider {
    appLoggerKermit(it)
}
