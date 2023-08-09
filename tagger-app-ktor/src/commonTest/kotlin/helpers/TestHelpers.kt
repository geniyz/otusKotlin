package site.geniyz.otus.app.helpers

import site.geniyz.otus.app.AppSettings
import site.geniyz.otus.app.auth.AuthConfig
import site.geniyz.otus.backend.repo.inmemory.RepoInMemory
import site.geniyz.otus.common.CorSettings
import site.geniyz.otus.common.repo.IRepository

fun testSettings(repo: IRepository? = null) = AppSettings(
    corSettings = CorSettings(
        repoTest = repo ?: RepoInMemory(),
        repoProd = repo ?: RepoInMemory(),
    ),
    auth = AuthConfig.TEST
)
