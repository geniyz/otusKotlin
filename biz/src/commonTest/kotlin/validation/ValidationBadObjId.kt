package site.geniyz.otus.biz.validation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import site.geniyz.otus.biz.AppProcessor
import site.geniyz.otus.common.*
import site.geniyz.otus.common.models.*
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@OptIn(ExperimentalCoroutinesApi::class)
fun validationIdCorrect(command: AppCommand, processor: AppProcessor) = runTest {
    val ctx = AppContext(
        command = command,
        state = AppState.NONE,
        workMode = AppWorkMode.TEST,
        objRequest = AppObj(
            id = AppObjId("123-234-abc-ABC"),
            name = "abc",
            content = "abc",
            objType = AppObjType.TEXT,
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(AppState.FAILING, ctx.state)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationIdTrim(command: AppCommand, processor: AppProcessor) = runTest {
    val ctx = AppContext(
        command = command,
        state = AppState.NONE,
        workMode = AppWorkMode.TEST,
        objRequest = AppObj(
            id = AppObjId(" \n\t 123-234-abc-ABC \n\t "),
            name = "abc",
            content = "abc",
            objType = AppObjType.TEXT,
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(AppState.FAILING, ctx.state)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationIdEmpty(command: AppCommand, processor: AppProcessor) = runTest {
    val ctx = AppContext(
        command = command,
        state = AppState.NONE,
        workMode = AppWorkMode.TEST,
        objRequest = AppObj(
            id = AppObjId(""),
            name = "abc",
            content = "abc",
            objType = AppObjType.TEXT,
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(AppState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("id", error?.field)
    assertContains(error?.message ?: "", "id")
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationIdFormat(command: AppCommand, processor: AppProcessor) = runTest {
    val ctx = AppContext(
        command = command,
        state = AppState.NONE,
        workMode = AppWorkMode.TEST,
        objRequest = AppObj(
            id = AppObjId("!@#\$%^&*(),.{}"),
            name = "abc",
            content = "abc",
            objType = AppObjType.TEXT,
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(AppState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("id", error?.field)
    assertContains(error?.message ?: "", "id")
}
