package site.geniyz.otus.biz.validation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

import site.geniyz.otus.biz.*
import site.geniyz.otus.common.*
import site.geniyz.otus.common.models.*

@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationObjSearchTest {

    private val command = AppCommand.OBJ_SEARCH
    private val processor by lazy { AppProcessor() }

    @Test
    fun correctEmpty() = runTest {
        val ctx = AppContext(
            command = command,
            state = AppState.NONE,
            workMode = AppWorkMode.TEST,
            objFilterRequest = AppObjFilter()
        )
        processor.exec(ctx)
        assertEquals(0, ctx.errors.size)
        assertNotEquals(AppState.FAILING, ctx.state)
    }
}

