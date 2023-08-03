package site.geniyz.otus.backend.repo.inmemory

import site.geniyz.otus.backend.repo.tests.*
import site.geniyz.otus.common.repo.IRepository

class ObjRepoInMemoryUpdateTest : RepoObjUpdateTest() {
    override val repo: IRepository = RepoInMemory(
        initObjs = initObjects,
        randomUuid = { lockNew.asString() }
    )
}
