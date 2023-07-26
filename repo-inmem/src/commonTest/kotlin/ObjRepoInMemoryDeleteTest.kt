package site.geniyz.otus.backend.repository.inmemory

import site.geniyz.otus.backend.repo.tests.*
import site.geniyz.otus.common.repo.IRepository

class ObjRepoInMemoryDeleteTest : RepoObjDeleteTest() {
    override val repo: IRepository = RepoInMemory(
        initObjs = initObjects
    )
}
