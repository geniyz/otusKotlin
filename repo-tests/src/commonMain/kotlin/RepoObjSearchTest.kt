package site.geniyz.otus.backend.repo.tests

import kotlinx.coroutines.ExperimentalCoroutinesApi
import site.geniyz.otus.common.models.*
import site.geniyz.otus.common.repo.*
import kotlin.test.Test
import kotlin.test.assertEquals


@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoObjSearchTest {
    abstract val repo: IRepository

    protected open val initializedObjects: List<AppObj> = initObjects

    @Test
    fun searchOwner() = runRepoTest {
        val result = repo.searchObj(DbObjFilterRequest(ownerId = searchOwnerId))
        assertEquals(true, result.isSuccess)
        val expected = listOf(initializedObjects[1], initializedObjects[3]).sortedBy { it.id.asString() }
        assertEquals(expected, result.data?.sortedBy { it.id.asString() })
        assertEquals(emptyList(), result.errors)
    }

    @Test
    fun searchDealSide() = runRepoTest {
        val result = repo.searchObj(DbObjFilterRequest("aaa")  )
        assertEquals(true, result.isSuccess)
        val expected = listOf(initializedObjects[2], initializedObjects[4]).sortedBy { it.id.asString() }
        assertEquals(expected, result.data?.sortedBy { it.id.asString() })
        assertEquals(emptyList(), result.errors)
    }

    companion object: BaseInitObjs("search") {

        val searchOwnerId = AppUserId("owner-124")
        override val initObjects: List<AppObj> = listOf(
            createInitTestModelObj("xxx1"),
            createInitTestModelObj("aaa2", ownerId = searchOwnerId),
            createInitTestModelObj("zzz3", objType = AppObjType.NONE),
            createInitTestModelObj("aaa4", ownerId = searchOwnerId),
            createInitTestModelObj("yyy5", objType = AppObjType.HREF),
        )
    }
}
