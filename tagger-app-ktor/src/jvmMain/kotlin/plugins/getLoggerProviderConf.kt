package site.geniyz.otus.app.plugins

import io.ktor.server.application.*
import site.geniyz.otus.common.repo.IRepository
import site.geniyz.otus.backend.repo.sql.*
import site.geniyz.otus.logging.common.AppLoggerProvider
import site.geniyz.otus.logging.jvm.appLoggerLogback
import site.geniyz.otus.logging.kermit.appLoggerKermit

actual fun Application.getLoggerProviderConf(): AppLoggerProvider =
    when (val mode = environment.config.propertyOrNull("ktor.logger")?.getString()) {
        "logback", null -> AppLoggerProvider { appLoggerLogback(it) } // по-дефолту Logback
        "kmp"           -> AppLoggerProvider { appLoggerKermit(it)  }
        else -> throw Exception("Logger $mode is not allowed. Additted values are kmp and logback")
}

actual fun Application.getRepoProd(): IRepository = RepoSQL(
        SqlProperties(
            url      = environment.config.property("sql.url").getString(),
            user     = environment.config.property("sql.user").getString(),
            password = environment.config.property("sql.password").getString(),
            schema   = environment.config.property("sql.schema").getString(),
        )
    )
