package site.geniyz.otus.biz.stub

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest

import site.geniyz.otus.biz.AppProcessor
import site.geniyz.otus.common.AppContext
import site.geniyz.otus.common.CorSettings
import site.geniyz.otus.common.models.*
import site.geniyz.otus.common.stubs.AppStubs
import site.geniyz.otus.stubs.AppStubObjs

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail

@OptIn(ExperimentalCoroutinesApi::class)
class ObjSearchStubTest {

    private val processor = AppProcessor(CorSettings())
    val filter = AppObjFilter(searchString = "blablabla")

    @Test
    fun read() = runTest {

        val ctx = AppContext(
            command = AppCommand.OBJ_SEARCH,
            state = AppState.NONE,
            workMode = AppWorkMode.STUB,
            stubCase = AppStubs.SUCCESS,
            objFilterRequest = filter,
        )
        processor.exec(ctx)
        assertTrue(ctx.objsResponse.size > 1)
        val first = ctx.objsResponse.firstOrNull() ?: fail("Empty response objects list")
        assertTrue(first.name.contains(filter.searchString))
        assertTrue(first.content.contains(filter.searchString))
        with (AppStubObjs.getText()) {
            assertEquals(objType, first.objType)
        }
    }

    @Test
    fun badId() = runTest {
        val ctx = AppContext(
            command = AppCommand.OBJ_SEARCH,
            state = AppState.NONE,
            workMode = AppWorkMode.STUB,
            stubCase = AppStubs.BAD_ID,
            objFilterRequest = filter,
        )
        processor.exec(ctx)
        assertEquals(AppObj(), ctx.objResponse)
        assertEquals("id", ctx.errors.firstOrNull()?.field)
        assertEquals("validation-obj", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = AppContext(
            command = AppCommand.OBJ_SEARCH,
            state = AppState.NONE,
            workMode = AppWorkMode.STUB,
            stubCase = AppStubs.DB_ERROR,
            objFilterRequest = filter,
        )
        processor.exec(ctx)
        assertEquals(AppObj(), ctx.objResponse)
        assertEquals("internal", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badNoCase() = runTest {
        val ctx = AppContext(
            command = AppCommand.OBJ_SEARCH,
            state = AppState.NONE,
            workMode = AppWorkMode.STUB,
            stubCase = AppStubs.BAD_NAME,
            objFilterRequest = filter,
        )
        processor.exec(ctx)
        assertEquals(AppObj(), ctx.objResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
    }
}
