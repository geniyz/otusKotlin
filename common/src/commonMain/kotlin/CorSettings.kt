package site.geniyz.otus.common

import site.geniyz.otus.common.repo.IRepository

data class CorSettings(
    val repoStub: IRepository = IRepository.NONE,
    val repoTest: IRepository = IRepository.NONE,
    val repoProd: IRepository = IRepository.NONE,
) {
    companion object {
        val NONE = CorSettings()
    }
}
