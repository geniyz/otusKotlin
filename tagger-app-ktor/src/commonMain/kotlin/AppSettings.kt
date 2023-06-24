package site.geniyz.otus.app

import site.geniyz.otus.biz.AppProcessor
import site.geniyz.otus.logging.common.AppLoggerProvider

data class AppSettings(
    val appUrls: List<String>,
    val processor: AppProcessor,
    val loggerProvider: AppLoggerProvider,
)
