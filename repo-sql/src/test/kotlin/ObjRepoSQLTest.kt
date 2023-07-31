package site.geniyz.otus.backend.repo.sql

import site.geniyz.otus.backend.repo.tests.*
import site.geniyz.otus.common.repo.IRepository

class ObjRepoSQLCreateTest : RepoObjCreateTest() {
    override val repo: IRepository = SqlTestCompanion.repoUnderTestContainer(
        initObjects,
        randomUuid = { lockNew.asString() },
    )
}

class ObjRepoSQLDeleteTest : RepoObjDeleteTest() {
    override val repo: IRepository = SqlTestCompanion.repoUnderTestContainer(initObjects)
}

class ObjRepoSQLReadTest : RepoObjReadTest() {
    override val repo: IRepository = SqlTestCompanion.repoUnderTestContainer(initObjects)
}

class ObjRepoSQLSearchTest : RepoObjSearchTest() {
    override val repo: IRepository = SqlTestCompanion.repoUnderTestContainer(initObjects)
}

class ObjRepoSQLUpdateTest : RepoObjUpdateTest() {
    override val repo: IRepository = SqlTestCompanion.repoUnderTestContainer(
        initObjects,
        randomUuid = { lockNew.asString() },
    )
}
