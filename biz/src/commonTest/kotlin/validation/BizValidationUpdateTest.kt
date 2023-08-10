package site.geniyz.otus.biz.validation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import site.geniyz.otus.backend.repo.stubs.RepoStub
import kotlin.test.Test
import site.geniyz.otus.biz.*
import site.geniyz.otus.common.CorSettings
import site.geniyz.otus.common.models.*

@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationObjUpdateTest {

    private val command = AppCommand.OBJ_UPDATE
    private val processor by lazy { AppProcessor(CorSettings(repoTest = RepoStub())) }

    @Test fun correctName()    = validationObjNameCorrect(command, processor)
    @Test fun trimName()       = validationObjNameTrim(command, processor)
    @Test fun emptyName()      = validationObjNameEmpty(command, processor)
    @Test fun badSymbolsName() = validationObjNameSymbols(command, processor)

    @Test fun correctContent()    = validationObjContentCorrect(command, processor)
    @Test fun trimContent()       = validationObjContentTrim(command, processor)
    @Test fun emptyContent()      = validationObjContentEmpty(command, processor)
    @Test fun badSymbolsContent() = validationObjContentSymbols(command, processor)

    @Test fun correctId()   = validationIdCorrect(command, processor)
    @Test fun trimId()      = validationIdTrim(command, processor)
    @Test fun emptyId()     = validationIdEmpty(command, processor)
    @Test fun badFormatId() = validationIdFormat(command, processor)


}

