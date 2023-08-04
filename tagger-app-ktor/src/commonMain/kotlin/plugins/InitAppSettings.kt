package site.geniyz.otus.app.plugins

import io.ktor.server.application.*
import site.geniyz.otus.app.*
import site.geniyz.otus.app.auth.AuthConfig
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
        auth           = initAppAuth(),
    )
}

expect fun Application.getLoggerProviderConf(): AppLoggerProvider

expect fun Application.getRepoProd(): IRepository

private fun Application.initAppAuth(): AuthConfig = AuthConfig(
    secret   = environment.config.propertyOrNull("jwt.secret")?.getString() ?: "",
    issuer   = environment.config.property("jwt.issuer").getString(),
    audience = environment.config.property("jwt.audience").getString(),
    realm    = environment.config.property("jwt.realm").getString(),
    clientId = environment.config.property("jwt.clientId").getString(),
    certUrl  = environment.config.propertyOrNull("jwt.certUrl")?.getString(),
)
