package site.geniyz.otus.backend.repo.tests

import kotlinx.coroutines.ExperimentalCoroutinesApi
import site.geniyz.otus.common.models.*
import site.geniyz.otus.common.repo.*
import kotlin.test.Test
import kotlin.test.assertEquals


@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoObjReadTest {
    abstract val repo: IRepository
    protected open val readSucc = initObjects[0]

    @Test
    fun readSuccess() = runRepoTest {
        val result = repo.readObj(DbObjIdRequest(readSucc.id))

        assertEquals(true, result.isSuccess)
        assertEquals(readSucc, result.data)
        assertEquals(emptyList(), result.errors)
    }

    @Test
    fun readNotFound() = runRepoTest {
        val result = repo.readObj(DbObjIdRequest(notFoundId))

        assertEquals(false, result.isSuccess)
        assertEquals(null, result.data)
        val error = result.errors.find { it.code == "not-found" }
        assertEquals("id", error?.field)
    }

    companion object : BaseInitObjs("delete") {
        override val initObjects: List<AppObj> = listOf(
            createInitTestModelObj("read")
        )

        val notFoundId = AppObjId("obj-repo-read-notFound")

    }
}
