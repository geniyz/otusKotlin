package site.geniyz.otus.biz.repo

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import site.geniyz.otus.backend.repo.tests.RepositoryMock
import site.geniyz.otus.biz.*
import site.geniyz.otus.common.*
import site.geniyz.otus.common.models.*
import site.geniyz.otus.common.repo.DbObjResponse
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class BizRepoUpdateTest {

    private val userId = AppUserId("000")
    private val command = AppCommand.OBJ_UPDATE
    private val initObj = AppObj(
        id = AppObjId("123"),
        name     = "abc",
        content  = "abc",
        authorId = userId,
        objType  = AppObjType.TEXT,
    )
    private val repo by lazy { RepositoryMock(
        invokeReadObj = {
            DbObjResponse(
                isSuccess = true,
                data = initObj,
            )
        },
        invokeUpdateObj = {
            DbObjResponse(
                isSuccess = true,
                data = AppObj(
                    id = AppObjId("123"),
                    name = "xyz",
                    content = "xyz",
                    objType = AppObjType.TEXT,
                )
            )
        }
    ) }
    private val settings by lazy {
        CorSettings(
            repoTest = repo
        )
    }
    private val processor by lazy { AppProcessor(settings) }

    @Test
    fun repoUpdateSuccessTest() = runTest {
        val objToUpdate = AppObj(
            id = AppObjId("123"),
            name    = "xyz",
            content = "xyz",
            objType = AppObjType.TEXT,
            lock    = AppLock("123-234-abc-ABC"),
        )
        val ctx = AppContext(
            command    = command,
            state      = AppState.NONE,
            workMode   = AppWorkMode.TEST,
            objRequest = objToUpdate,
        )
        ctx.addTestPrincipal(userId)
        processor.exec(ctx)
        println(ctx)
        assertEquals(AppState.FINISHING, ctx.state)
        assertEquals(objToUpdate.id,      ctx.objResponse.id)
        assertEquals(objToUpdate.name,    ctx.objResponse.name)
        assertEquals(objToUpdate.content, ctx.objResponse.content)
        assertEquals(objToUpdate.objType, ctx.objResponse.objType)
    }

    @Test
    fun repoUpdateNotFoundTest() = repoNotFoundTest(command)
}
