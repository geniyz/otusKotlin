package site.geniyz.otus.app.plugins

import io.ktor.server.application.*
import site.geniyz.otus.app.AppSettings
import site.geniyz.otus.biz.AppProcessor
import site.geniyz.otus.logging.common.AppLoggerProvider

fun Application.initAppSettings(): AppSettings = AppSettings(
    appUrls = environment.config.propertyOrNull("ktor.urls")?.getList() ?: emptyList(),
    processor = AppProcessor(),
    loggerProvider = getLoggerProviderConf(),
)

expect fun Application.getLoggerProviderConf(): AppLoggerProvider
