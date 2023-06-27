package site.geniyz.otus.app

import site.geniyz.otus.biz.AppProcessor
import site.geniyz.otus.logging.common.AppLoggerProvider
// import site.geniyz.otus.common.AppCorSettings

data class AppSettings(
    val appUrls: List<String>,
    val processor: AppProcessor,
    val loggerProvider: AppLoggerProvider,
    // val corSettings: AppCorSettings,
)
