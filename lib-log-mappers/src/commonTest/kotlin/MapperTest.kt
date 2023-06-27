import site.geniyz.otus.api.logs.mapper.toLog
import site.geniyz.otus.common.AppContext
import site.geniyz.otus.common.models.*
import kotlin.test.Test
import kotlin.test.assertEquals

class MapperTest {

    @Test
    fun fromContext() {
        val context = AppContext(
            requestId = AppRequestId("1234"),
            command = AppCommand.OBJ_CREATE,
            objResponse = AppObj(
                name = "!@#",
                content = "Content",
                objType = AppObjType.TEXT,
            ),
            errors = mutableListOf(
                AppError(
                    code = "err",
                    group = "request",
                    field = "name",
                    message = "wrong name",
                )
            ),
            state = AppState.RUNNING,
        )

        val log = context.toLog("test-id")

        assertEquals("test-id", log.logId)
        assertEquals("tagger", log.source)
        assertEquals("1234", log.obj?.requestId)
        val error = log.errors?.firstOrNull()
        assertEquals("wrong name", error?.message)
        assertEquals("ERROR", error?.level)
    }
}
