package site.geniyz.otus.biz.repo

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import site.geniyz.otus.backend.repo.tests.RepositoryMock
import site.geniyz.otus.biz.*
import site.geniyz.otus.common.*
import site.geniyz.otus.common.models.*
import site.geniyz.otus.common.repo.*
import kotlin.test.assertEquals

private val initObj = AppObj(
    id = AppObjId("123"),
    name    = "abc",
    content = "abc",
    objType = AppObjType.TEXT,
)
private val repo = RepositoryMock(
        invokeReadObj = {
            if (it.id == initObj.id) {
                DbObjResponse(
                    isSuccess = true,
                    data = initObj,
                )
            } else DbObjResponse(
                isSuccess = false,
                data = null,
                errors = listOf(AppError(message = "Not found", field = "id"))
            )
        }
    )
private val settings by lazy {
    CorSettings(
        repoTest = repo
    )
}
private val processor by lazy { AppProcessor(settings) }

@OptIn(ExperimentalCoroutinesApi::class)
fun repoNotFoundTest(command: AppCommand) = runTest {
    val ctx = AppContext(
        command = command,
        state = AppState.NONE,
        workMode = AppWorkMode.TEST,
        objRequest = AppObj(
            id = AppObjId("12345"),
            name    = "xyz",
            content = "xyz",
            objType = AppObjType.TEXT,
            lock = AppLock("123-234-abc-ABC"),
        ),
    )
    ctx.addTestPrincipal()
    processor.exec(ctx)
    assertEquals(AppState.FAILING, ctx.state)
    assertEquals(AppObj(), ctx.objResponse)
    assertEquals(1, ctx.errors.size)
    assertEquals("id", ctx.errors.first().field)
}
