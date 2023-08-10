package site.geniyz.otus.biz.repo

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import site.geniyz.otus.backend.repo.tests.*
import site.geniyz.otus.biz.*
import site.geniyz.otus.common.*
import site.geniyz.otus.common.models.*
import site.geniyz.otus.common.repo.DbObjResponse
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class BizRepoDeleteTest {

    private val userId = AppUserId("000")
    private val command = AppCommand.OBJ_DELETE
    private val initObj = AppObj(
        id = AppObjId("123"),
        name     = "abc",
        content  = "abc",
        authorId = userId,
        objType  = AppObjType.TEXT,
        lock     = AppLock("123-234-abc-ABC"),
    )
    private val repo by lazy {
        RepositoryMock(
            invokeReadObj = {
               DbObjResponse(
                   isSuccess = true,
                   data = initObj,
               )
            },
            invokeDeleteObj = {
                if (it.id == initObj.id)
                    DbObjResponse(
                        isSuccess = true,
                        data = initObj
                    )
                else DbObjResponse(isSuccess = false, data = null)
            }
        )
    }
    private val settings by lazy {
        CorSettings(
            repoTest = repo
        )
    }
    private val processor by lazy { AppProcessor(settings) }

    @Test
    fun repoDeleteSuccessTest() = runTest {
        val obj = AppObj(
            id   = AppObjId("123"),
            lock = AppLock("123-234-abc-ABC"),
        )
        val ctx = AppContext(
            command = command,
            state = AppState.NONE,
            workMode = AppWorkMode.TEST,
            objRequest = obj,
        )
        ctx.addTestPrincipal(userId)
        processor.exec(ctx)
        assertEquals(AppState.FINISHING, ctx.state)
        assertTrue { ctx.errors.isEmpty() }
        assertEquals(initObj.id, ctx.objResponse.id)
        assertEquals(initObj.name,    ctx.objResponse.name)
        assertEquals(initObj.content, ctx.objResponse.content)
        assertEquals(initObj.objType, ctx.objResponse.objType)
    }

    @Test
    fun repoDeleteNotFoundTest() = repoNotFoundTest(command)
}
