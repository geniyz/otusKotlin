package site.geniyz.otus.backend.repo.tests

import kotlinx.coroutines.ExperimentalCoroutinesApi
import site.geniyz.otus.common.models.*
import site.geniyz.otus.common.repo.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoObjCreateTest {
    abstract val repo: IRepository

    protected open val lockNew: AppLock = AppLock("20000000-0000-0000-0000-000000000002")

    private val createObj = AppObj(
        name = "name of object",
        content = "object description",
        authorId = AppUserId("owner-123"),
        objType = AppObjType.TEXT
    )

    @Test
    fun createSuccess() = runRepoTest {
        val result = repo.createObj(DbObjRequest(createObj))
        val expected = createObj.copy(id = result.data?.id ?: AppObjId.NONE)
        assertEquals(true, result.isSuccess)
        assertEquals(expected.name, result.data?.name)
        assertEquals(expected.content, result.data?.content)
        assertEquals(expected.objType, result.data?.objType)
        assertNotEquals(AppObjId.NONE, result.data?.id)
        assertEquals(emptyList(), result.errors)
        assertEquals(lockNew, result.data?.lock)
    }

    companion object : BaseInitObjs("create") {
        override val initObjects: List<AppObj> = emptyList()
    }
}
