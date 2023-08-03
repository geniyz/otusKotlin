package site.geniyz.otus.backend.repo.inmemory

import site.geniyz.otus.backend.repo.tests.*
import site.geniyz.otus.common.repo.IRepository

class ObjRepoInMemoryReadTest: RepoObjReadTest() {
    override val repo: IRepository = RepoInMemory(
        initObjs = initObjects
    )
}
