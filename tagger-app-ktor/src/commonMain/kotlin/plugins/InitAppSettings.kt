package site.geniyz.otus.app.plugins

import io.ktor.server.application.*
import site.geniyz.otus.app.AppSettings
import site.geniyz.otus.biz.AppProcessor
import site.geniyz.otus.logging.common.AppLoggerProvider
import site.geniyz.otus.backend.repo.inmemory.RepoInMemory
import site.geniyz.otus.common.CorSettings
import site.geniyz.otus.common.repo.IRepository

fun Application.initAppSettings(): AppSettings {
    val corSettings = CorSettings(
        repoTest = RepoInMemory(),
        repoStub = RepoInMemory(),
        repoProd = getRepoProd()
    )
    return AppSettings(
        appUrls = environment.config.propertyOrNull("ktor.urls")?.getList() ?: emptyList(), // TODO: не понятно что это и зачем
        loggerProvider = getLoggerProviderConf(),
        corSettings    = corSettings,
        processor      = AppProcessor(corSettings),
    )
}

expect fun Application.getLoggerProviderConf(): AppLoggerProvider

expect fun Application.getRepoProd(): IRepository
