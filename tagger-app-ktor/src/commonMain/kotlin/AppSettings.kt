package site.geniyz.otus.app

import site.geniyz.otus.app.plugins.getLoggerProviderConf
import site.geniyz.otus.biz.AppProcessor
import site.geniyz.otus.common.repo.IRepository
import site.geniyz.otus.logging.common.AppLoggerProvider
import site.geniyz.otus.common.CorSettings

data class AppSettings(
    val appUrls:        List<String> = emptyList(), // пока не понимаю что это и зачем
    val loggerProvider: AppLoggerProvider = AppLoggerProvider(),
    val corSettings:    CorSettings,
    val processor:      AppProcessor = AppProcessor(corSettings),

    // val repoProd: IRepository = IRepository.NONE,
    // val repoTest: IRepository = IRepository.NONE,
    // val repoStub: IRepository = IRepository.NONE,
)
