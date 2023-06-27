package site.geniyz.otus.biz.validation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import site.geniyz.otus.biz.AppProcessor
import site.geniyz.otus.common.AppContext
import site.geniyz.otus.common.models.*
import site.geniyz.otus.stubs.AppStubObjs
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

private val stub = AppStubObjs.getText()

@OptIn(ExperimentalCoroutinesApi::class)
fun validationObjNameCorrect(command: AppCommand, processor: AppProcessor) = runTest {
    val ctx = AppContext(
        command = command,
        state = AppState.NONE,
        workMode = AppWorkMode.TEST,
        objRequest = AppObj(
            id = stub.id,
            name = "abc",
            content = "abc",
            objType = AppObjType.TEXT,
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(AppState.FAILING, ctx.state)
    assertEquals("abc", ctx.objValidated.name)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationObjNameTrim(command: AppCommand, processor: AppProcessor) = runTest {
    val ctx = AppContext(
        command = command,
        state = AppState.NONE,
        workMode = AppWorkMode.TEST,
        objRequest = AppObj(
            id = stub.id,
            name = " \n\t abc \t\n ",
            content = "abc",
            objType = AppObjType.TEXT,
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(AppState.FAILING, ctx.state)
    assertEquals("abc", ctx.objValidated.name)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationObjNameEmpty(command: AppCommand, processor: AppProcessor) = runTest {
    val ctx = AppContext(
        command = command,
        state = AppState.NONE,
        workMode = AppWorkMode.TEST,
        objRequest = AppObj(
            id = stub.id,
            name = "",
            content = "abc",
            objType = AppObjType.TEXT,
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(AppState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("name", error?.field)
    assertContains(error?.message ?: "", "name")
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationObjNameSymbols(command: AppCommand, processor: AppProcessor) = runTest {
    val ctx = AppContext(
        command = command,
        state = AppState.NONE,
        workMode = AppWorkMode.TEST,
        objRequest = AppObj(
            id = AppObjId("123"),
            name = "!@#$%^&*(),.{}",
            content = "abc",
            objType = AppObjType.TEXT,
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(AppState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("name", error?.field)
    assertContains(error?.message ?: "", "name")
}
