package site.geniyz.otus.biz.validation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.test.Test

import site.geniyz.otus.biz.*
import site.geniyz.otus.common.models.*


@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationObjCreateTest {

    private val command = AppCommand.OBJ_CREATE
    private val processor by lazy { AppProcessor() }

    @Test fun correctObjName()    = validationObjNameCorrect(command, processor)
    @Test fun trimObjName()       = validationObjNameTrim(command, processor)
    @Test fun emptyObjName()      = validationObjNameEmpty(command, processor)
    @Test fun badSymbolsObjName() = validationObjNameSymbols(command, processor)

    @Test fun correctObjContent()    = validationObjContentCorrect(command, processor)
    @Test fun trimObjContent()       = validationObjContentTrim(command, processor)
    @Test fun emptyObjContent()      = validationObjContentEmpty(command, processor)
    @Test fun badSymbolsObjContent() = validationObjContentSymbols(command, processor)

}

