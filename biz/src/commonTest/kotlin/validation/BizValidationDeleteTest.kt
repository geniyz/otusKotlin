package site.geniyz.otus.biz.validation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.test.Test

import site.geniyz.otus.biz.*
import site.geniyz.otus.common.CorSettings
import site.geniyz.otus.common.models.*

@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationObjDeleteTest {

    private val command = AppCommand.OBJ_DELETE
    private val processor by lazy { AppProcessor(CorSettings()) }

    @Test fun correctId()   = validationIdCorrect(command, processor)
    @Test fun trimId()      = validationIdTrim(command, processor)
    @Test fun emptyId()     = validationIdEmpty(command, processor)
    @Test fun badFormatId() = validationIdFormat(command, processor)

}
