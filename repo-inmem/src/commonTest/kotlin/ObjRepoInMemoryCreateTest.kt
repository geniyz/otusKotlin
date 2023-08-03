package site.geniyz.otus.backend.repo.inmemory

import site.geniyz.otus.backend.repo.tests.*

class ObjRepoInMemoryCreateTest : RepoObjCreateTest() {
    override val repo = RepoInMemory(
        initObjs = initObjects,
        randomUuid = { lockNew.asString() }
    )
}