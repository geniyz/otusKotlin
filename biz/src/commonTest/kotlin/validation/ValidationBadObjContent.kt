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
fun validationObjContentCorrect(command: AppCommand, processor: AppProcessor) = runTest {
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
    assertEquals("abc", ctx.objValidated.content)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationObjContentTrim(command: AppCommand, processor: AppProcessor) = runTest {
    val ctx = AppContext(
        command = command,
        state = AppState.NONE,
        workMode = AppWorkMode.TEST,
        objRequest = AppObj(
            id = stub.id,
            name = "abc",
            content = " \n\tabc \n\t",
            objType = AppObjType.TEXT,
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(AppState.FAILING, ctx.state)
    assertEquals("abc", ctx.objValidated.content)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationObjContentEmpty(command: AppCommand, processor: AppProcessor) = runTest {
    val ctx = AppContext(
        command = command,
        state = AppState.NONE,
        workMode = AppWorkMode.TEST,
        objRequest = AppObj(
            id = stub.id,
            name = "abc",
            content = "",
            objType = AppObjType.TEXT,
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(AppState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("content", error?.field)
    assertContains(error?.message ?: "", "content")
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationObjContentSymbols(command: AppCommand, processor: AppProcessor) = runTest {
    val ctx = AppContext(
        command = command,
        state = AppState.NONE,
        workMode = AppWorkMode.TEST,
        objRequest = AppObj(
            id = stub.id,
            name = "abc",
            content = "!@#$%^&*(),.{}",
            objType = AppObjType.TEXT,
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(AppState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("content", error?.field)
    assertContains(error?.message ?: "", "content")
}
