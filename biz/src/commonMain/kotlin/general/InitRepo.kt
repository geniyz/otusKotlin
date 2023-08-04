package site.geniyz.otus.biz.general

import site.geniyz.otus.common.AppContext
import site.geniyz.otus.common.helpers.*
import site.geniyz.otus.common.models.AppWorkMode
import site.geniyz.otus.common.repo.IRepository
import site.geniyz.otus.cor.ICorChainDsl
import site.geniyz.otus.cor.worker

fun ICorChainDsl<AppContext>.initRepo(title: String) = worker {
    this.title = title
    description = """
        Вычисление основного рабочего репозитория в зависимости от запрошенного режима работы        
    """.trimIndent()
    handle {
        repo = when (workMode) {
            AppWorkMode.TEST -> settings.repoTest
            AppWorkMode.STUB -> settings.repoStub
            else -> settings.repoProd
        }
        if (workMode != AppWorkMode.STUB && repo == IRepository.NONE) fail(
            errorAdministration(
                field = "repo",
                violationCode = "dbNotConfigured",
                description = "The database is unconfigured for chosen workmode ($workMode). " +
                        "Please, contact the administrator staff"
            )
        )
    }
}
