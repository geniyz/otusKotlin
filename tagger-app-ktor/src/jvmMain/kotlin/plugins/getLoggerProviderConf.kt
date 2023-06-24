package site.geniyz.otus.app.plugins

import io.ktor.server.application.*
import site.geniyz.otus.logging.common.AppLoggerProvider
import site.geniyz.otus.logging.jvm.appLoggerLogback
import site.geniyz.otus.logging.kermit.appLoggerKermit

actual fun Application.getLoggerProviderConf(): AppLoggerProvider =
    when (val mode = environment.config.propertyOrNull("ktor.logger")?.getString()) {
        "logback", null -> AppLoggerProvider { appLoggerLogback(it) } // по-дефолту Logback
        "kmp"           -> AppLoggerProvider { appLoggerKermit(it)  }
        else -> throw Exception("Logger $mode is not allowed. Additted values are kmp and logback")
}
