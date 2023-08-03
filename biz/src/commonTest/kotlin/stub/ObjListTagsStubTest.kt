package site.geniyz.otus.biz.stub

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest

import site.geniyz.otus.biz.AppProcessor
import site.geniyz.otus.common.AppContext
import site.geniyz.otus.common.CorSettings
import site.geniyz.otus.common.models.*
import site.geniyz.otus.common.stubs.AppStubs

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail

@OptIn(ExperimentalCoroutinesApi::class)
class ObjOffersStubTest {

    private val processor = AppProcessor(CorSettings())
    val id = AppObjId("777")

    @Test
    fun offers() = runTest {

        val ctx = AppContext(
            command = AppCommand.OBJ_LIST_TAGS,
            state = AppState.NONE,
            workMode = AppWorkMode.STUB,
            stubCase = AppStubs.SUCCESS,
            objRequest = AppObj(
                id = id,
            ),
        )
        processor.exec(ctx)

        assertEquals(id, ctx.objResponse.id)

        assertTrue(ctx.tagsResponse.size > 1)
        val first = ctx.tagsResponse.firstOrNull() ?: fail("Empty response tags list")
        assertTrue(first.code.contains(""))
    }

    @Test
    fun badId() = runTest {
        val ctx = AppContext(
            command = AppCommand.OBJ_LIST_TAGS,
            state = AppState.NONE,
            workMode = AppWorkMode.STUB,
            stubCase = AppStubs.BAD_ID,
            objRequest = AppObj(
                id = id,
            ),
        )
        processor.exec(ctx)
        assertEquals(AppObj(), ctx.objResponse)
        assertEquals("id", ctx.errors.firstOrNull()?.field)
        assertEquals("validation-obj", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = AppContext(
            command = AppCommand.OBJ_LIST_TAGS,
            state = AppState.NONE,
            workMode = AppWorkMode.STUB,
            stubCase = AppStubs.DB_ERROR,
            objRequest = AppObj(
                id = id,
            ),
        )
        processor.exec(ctx)
        assertEquals(AppObj(), ctx.objResponse)
        assertEquals("internal", ctx.errors.firstOrNull()?.group)
    }

}
