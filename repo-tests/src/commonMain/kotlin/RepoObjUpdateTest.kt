package site.geniyz.otus.backend.repo.tests

import kotlinx.coroutines.ExperimentalCoroutinesApi
import site.geniyz.otus.common.models.*
import site.geniyz.otus.common.repo.*
import kotlin.test.Test
import kotlin.test.assertEquals


@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoObjUpdateTest {
    abstract val repo: IRepository
    protected open val updateSucc = initObjects[0]
    protected open val updateConc = initObjects[1]
    protected val updateIdNotFound = AppObjId("obj-repo-update-not-found")
    protected val lockBad = AppLock("20000000-0000-0000-0000-000000000009")
    protected val lockNew = AppLock("20000000-0000-0000-0000-000000000002")

    private val reqUpdateSucc by lazy {
        AppObj(
            id = updateSucc.id,
            name = "update object",
            content = "update object description",
            authorId = AppUserId("owner-123"),
            objType = AppObjType.TEXT,
            lock = initObjects.first().lock,
        )
    }
    private val reqUpdateNotFound = AppObj(
        id = updateIdNotFound,
        name = "update object not found",
        content = "update object not found description",
        authorId = AppUserId("owner-123"),
        objType = AppObjType.TEXT,
        lock = initObjects.first().lock,
    )
    private val reqUpdateConc by lazy {
        AppObj(
            id = updateConc.id,
            name = "update object not found",
            content = "update object not found description",
            authorId = AppUserId("owner-123"),
            objType = AppObjType.TEXT,
            lock = lockBad,
        )
    }

    @Test
    fun updateSuccess() = runRepoTest {
        val result = repo.updateObj(DbObjRequest(reqUpdateSucc))
        assertEquals(true, result.isSuccess)
        assertEquals(reqUpdateSucc.id, result.data?.id)
        assertEquals(reqUpdateSucc.name, result.data?.name)
        assertEquals(reqUpdateSucc.content, result.data?.content)
        assertEquals(reqUpdateSucc.objType, result.data?.objType)
        assertEquals(emptyList(), result.errors)
    }

    @Test
    fun updateNotFound() = runRepoTest {
        val result = repo.updateObj(DbObjRequest(reqUpdateNotFound))
        assertEquals(false, result.isSuccess)
        assertEquals(null, result.data)
        val error = result.errors.find { it.code == "not-found" }
        assertEquals("id", error?.field)
    }

    @Test
    fun updateConcurrencyError() = runRepoTest {
        val result = repo.updateObj(DbObjRequest(reqUpdateConc))
        assertEquals(false, result.isSuccess)
        val error = result.errors.find { it.code == "concurrency" }
        assertEquals("lock", error?.field)
        assertEquals(updateConc, result.data)
    }

    companion object : BaseInitObjs("update") {
        override val initObjects: List<AppObj> = listOf(
            createInitTestModelObj("update"),
            createInitTestModelObj("updateConc"),
        )
    }
}
