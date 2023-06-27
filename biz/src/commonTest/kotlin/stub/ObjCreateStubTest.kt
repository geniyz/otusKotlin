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
class ObjCreateStubTest {

    private val processor = AppProcessor()
    val id = AppObjId("666")
    val name    = "title 666"
    val content = "content 666"
    val objType = AppObjType.TEXT

    @Test
    fun create() = runTest {

        val ctx = AppContext(
            command = AppCommand.OBJ_CREATE,
            state = AppState.NONE,
            workMode = AppWorkMode.STUB,
            stubCase = AppStubs.SUCCESS,
            objRequest = AppObj(
                id = id,
                name = name,
                content = content,
                objType = objType,
            ),
        )
        processor.exec(ctx)
        assertEquals(AppStubObjs.getText().id, ctx.objResponse.id)
        assertEquals(name, ctx.objResponse.name)
        assertEquals(content, ctx.objResponse.content)
        assertEquals(objType, ctx.objResponse.objType)
    }

    @Test
    fun badName() = runTest {
        val ctx = AppContext(
            command = AppCommand.OBJ_CREATE,
            state = AppState.NONE,
            workMode = AppWorkMode.STUB,
            stubCase = AppStubs.BAD_NAME,
            objRequest = AppObj(
                id = id,
                name = "",
                content = content,
                objType = objType,
            ),
        )
        processor.exec(ctx)
        assertEquals(AppObj(), ctx.objResponse)
        assertEquals("name", ctx.errors.firstOrNull()?.field)
        assertEquals("validation-obj", ctx.errors.firstOrNull()?.group)
    }


    @Test
    fun badContent() = runTest {
        val ctx = AppContext(
            command = AppCommand.OBJ_CREATE,
            state = AppState.NONE,
            workMode = AppWorkMode.STUB,
            stubCase = AppStubs.BAD_CONTENT,
            objRequest = AppObj(
                id = id,
                name = name,
                content = "",
                objType = objType,
            ),
        )
        processor.exec(ctx)
        assertEquals(AppObj(), ctx.objResponse)
        assertEquals("content", ctx.errors.firstOrNull()?.field)
        assertEquals("validation-obj", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = AppContext(
            command = AppCommand.OBJ_CREATE,
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
            command = AppCommand.OBJ_CREATE,
            state = AppState.NONE,
            workMode = AppWorkMode.STUB,
            stubCase = AppStubs.BAD_ID,
            objRequest = AppObj(
                id = id,
                name = name,
                content = content,
                objType = objType,
            ),
        )
        processor.exec(ctx)
        assertEquals(AppObj(), ctx.objResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

}