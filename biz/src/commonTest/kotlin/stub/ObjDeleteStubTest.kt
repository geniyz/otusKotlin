package site.geniyz.otus.biz.stub

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest

import site.geniyz.otus.biz.AppProcessor
import site.geniyz.otus.common.AppContext
import site.geniyz.otus.common.models.*
import site.geniyz.otus.common.stubs.AppStubs
import site.geniyz.otus.stubs.AppStubObjs

import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class ObjDeleteStubTest {

    private val processor = AppProcessor()
    val id = AppObjId("666")

    @Test
    fun delete() = runTest {

        val ctx = AppContext(
            command = AppCommand.OBJ_DELETE,
            state = AppState.NONE,
            workMode = AppWorkMode.STUB,
            stubCase = AppStubs.SUCCESS,
            objRequest = AppObj(
                id = id,
            ),
        )
        processor.exec(ctx)

        val stub = AppStubObjs.getText()
        assertEquals(stub.id, ctx.objResponse.id)
        assertEquals(stub.name, ctx.objResponse.name)
        assertEquals(stub.content, ctx.objResponse.content)
        assertEquals(stub.objType, ctx.objResponse.objType)
    }

    @Test
    fun badId() = runTest {
        val ctx = AppContext(
            command = AppCommand.OBJ_DELETE,
            state = AppState.NONE,
            workMode = AppWorkMode.STUB,
            stubCase = AppStubs.BAD_ID,
            objRequest = AppObj()
        )
        processor.exec(ctx)
        assertEquals(AppObj(), ctx.objResponse)
        assertEquals("id", ctx.errors.firstOrNull()?.field)
        assertEquals("validation-obj", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = AppContext(
            command = AppCommand.OBJ_DELETE,
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

    @Test
    fun badNoCase() = runTest {
        val ctx = AppContext(
            command = AppCommand.OBJ_DELETE,
            state = AppState.NONE,
            workMode = AppWorkMode.STUB,
            stubCase = AppStubs.BAD_NAME,
            objRequest = AppObj(
                id = id,
            ),
        )
        processor.exec(ctx)
        assertEquals(AppObj(), ctx.objResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
    }
}
