package site.geniyz.otus.biz

import kotlinx.datetime.Clock
import site.geniyz.otus.biz.groups.*
import site.geniyz.otus.biz.workers.*

import site.geniyz.otus.common.AppContext
import site.geniyz.otus.common.models.*

import site.geniyz.otus.cor.*
import site.geniyz.otus.logging.common.*

import site.geniyz.otus.api.logs.mapper.toLog
import site.geniyz.otus.biz.validation.*

import site.geniyz.otus.common.helpers.asAppError
import site.geniyz.otus.common.helpers.fail

class AppProcessor {
    suspend fun exec(ctx: AppContext){
        AnyBusinessChain.exec(ctx)
        when( ctx.command.name.substringBefore("_") ){
            "OBJ" -> ObjBusinessChain.exec(ctx)
            "TAG" -> TagBusinessChain.exec(ctx)
            else  -> OtherBusinessChain.exec(ctx)
        }
    }

    suspend fun <T> process(
        logger: IAppLogWrapper,
        logId: String,
        command: AppCommand,
        fromTransport: suspend (AppContext) -> Unit,
        sendResponse: suspend (AppContext) -> T): T {

        val ctx = AppContext(
            timeStart = Clock.System.now(),
        )
        var realCommand = command

        return try {
            logger.doWithLogging(id = logId) {
                fromTransport(ctx)
                realCommand = ctx.command

                logger.info(
                    msg = "$realCommand request is got",
                    data = ctx.toLog("${logId}-got")
                )
                exec(ctx)
                logger.info(
                    msg = "$realCommand request is handled",
                    data = ctx.toLog("${logId}-handled")
                )
                sendResponse(ctx)
            }
        } catch (e: Throwable) {
            logger.doWithLogging(id = "${logId}-failure") {
                logger.error(msg = "$realCommand handling failed")

                ctx.command = realCommand
                ctx.fail(e.asAppError())
                exec(ctx)
                sendResponse(ctx)
            }
        }
    }

    companion object {
        private val AnyBusinessChain = rootChain<AppContext> {
            initStatus("Инициализация статуса")
        }.build()

        private val OtherBusinessChain = rootChain<AppContext> {
            stubNoCase("Ошибка: запрошенный стаб недопустим")
        }.build()

        private val ObjBusinessChain = rootChain<AppContext> {
            operation("Создание сущности", AppCommand.OBJ_CREATE) {
                stubs("Обработка стабов") {
                    stubObjCreateSuccess("Имитация успешной обработки")
                    stubObjValidationBadName("Имитация ошибки валидации заголовка")
                    stubObjValidationBadContent("Имитация ошибки валидации содержимого")

                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в objValidating") { objValidating = objRequest.copy() }
                    worker("Очистка id") { objValidating.id = AppObjId.NONE }
                    worker("Очистка заголовка") { objValidating.name = objValidating.name.trim() }
                    worker("Очистка описания") { objValidating.content = objValidating.content.trim() }

                    validateObjNameNotEmpty("Проверка, что заголовок не пуст")
                    validateObjNameHasContent("Проверка символов")
                    validateObjContentNotEmpty("Проверка, что описание не пусто")
                    validateObjContentHasContent("Проверка символов")

                    finishObjValidation("Завершение проверок")
                }
            }
            operation("Получить сущность", AppCommand.OBJ_READ) {
                stubs("Обработка стабов") {
                    stubObjReadSuccess("Имитация успешной обработки")
                    stubObjValidationBadId("Имитация ошибки валидации id")

                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в objValidating") { objValidating = objRequest.copy() }
                    worker("Очистка id") { objValidating.id = AppObjId(objValidating.id.asString().trim()) }

                    validateObjIdNotEmpty("Проверка на непустой id")
                    validateObjIdProperFormat("Проверка формата id")

                    finishObjValidation("Успешное завершение процедуры валидации")
                }
            }
            operation("Изменить сущность", AppCommand.OBJ_UPDATE) {
                stubs("Обработка стабов") {
                    stubObjUpdateSuccess("Имитация успешной обработки")
                    stubObjValidationBadId("Имитация ошибки валидации id")
                    stubObjValidationBadName("Имитация ошибки валидации заголовка")
                    stubObjValidationBadContent("Имитация ошибки валидации содержимого")

                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в objValidating") { objValidating = objRequest.copy() }
                    worker("Очистка id") { objValidating.id = AppObjId.NONE }
                    worker("Очистка заголовка") { objValidating.name = objValidating.name.trim() }
                    worker("Очистка описания") { objValidating.content = objValidating.content.trim() }

                    validateObjIdNotEmpty("Проверка на непустой id")
                    validateObjIdProperFormat("Проверка формата id")
                    validateObjNameNotEmpty("Проверка, что заголовок не пуст")
                    validateObjNameHasContent("Проверка символов")
                    validateObjContentNotEmpty("Проверка, что описание не пусто")
                    validateObjContentHasContent("Проверка символов")

                    finishObjValidation("Завершение проверок")
                }
            }
            operation("Удалить сущность", AppCommand.OBJ_DELETE) {
                stubs("Обработка стабов") {
                    stubObjDeleteSuccess("Имитация успешной обработки")
                    stubObjValidationBadId("Имитация ошибки валидации id")

                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в objValidating") { objValidating = objRequest.copy() }
                    worker("Очистка id") { objValidating.id = AppObjId(objValidating.id.asString().trim()) }

                    validateObjIdNotEmpty("Проверка на непустой id")
                    validateObjIdProperFormat("Проверка формата id")

                    finishObjValidation("Успешное завершение процедуры валидации")
                }
            }
            operation("Поиск сущности", AppCommand.OBJ_SEARCH) {
                stubs("Обработка стабов") {
                    stubObjSearchSuccess("Имитация успешной обработки")
                    stubObjValidationBadId("Имитация ошибки валидации id")

                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в objFilterValidating") { objFilterValidating = objFilterRequest.copy() }

                    finishObjFilterValidation("Успешное завершение процедуры валидации")
                }

            }
            operation("Получение меток объекта", AppCommand.OBJ_LIST_TAGS) {
                stubs("Обработка стабов") {
                    stubObjListTagsSuccess("Имитация успешной обработки")
                    stubObjValidationBadId("Имитация ошибки валидации id")

                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в objValidating") { objValidating = objRequest.copy() }
                    worker("Очистка id") { objValidating.id = AppObjId(objValidating.id.asString().trim()) }

                    validateObjIdNotEmpty("Проверка на непустой id")
                    validateObjIdProperFormat("Проверка формата id")

                    finishObjValidation("Успешное завершение процедуры валидации")
                }
            }
            operation("Изменение меток объекта", AppCommand.OBJ_SET_TAGS) {
                stubs("Обработка стабов") {
                    stubObjSetTagsSuccess("Имитация успешной обработки")
                    stubObjValidationBadId("Имитация ошибки валидации id")

                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в objValidating") { objValidating = objRequest.copy() }
                    worker("Копируем метки в tagsValidating") { tagsValidating = tagsRequest.toMutableList() }

                    worker("Очистка id") { objValidating.id = AppObjId(objValidating.id.asString().trim()) }

                    validateObjIdNotEmpty("Проверка на непустой id")
                    validateObjIdProperFormat("Проверка формата id")

                    finishObjValidation("Успешное завершение процедуры валидации")
                }
            }
        }.build()

        private val TagBusinessChain = rootChain<AppContext> {
            operation("Удалить сущность", AppCommand.TAG_DELETE) {
                stubs("Обработка стабов") {
                    stubTagDeleteSuccess("Имитация успешной обработки")
                    stubTagValidationBadId("Имитация ошибки валидации id")

                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в tagValidating") { tagValidating = tagRequest.copy() }
                    worker("Очистка id") { tagValidating.id = AppTagId(tagValidating.id.asString().trim()) }

                    validateTagIdNotEmpty("Проверка на непустой id")
                    validateTagIdProperFormat("Проверка формата id")

                    finishTagValidation("Успешное завершение процедуры валидации")
                }
            }
            operation("Поиск метки", AppCommand.TAG_SEARCH) {
                stubs("Обработка стабов") {
                    stubTagSearchSuccess("Имитация успешной обработки")
                    stubTagValidationBadId("Имитация ошибки валидации id")

                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в tagFilterValidating") { tagFilterValidating = tagFilterRequest.copy() }

                    finishTagFilterValidation("Успешное завершение процедуры валидации")
                }

            }
            operation("Получение объедков метки", AppCommand.TAG_LIST_OBJS) {
                stubs("Обработка стабов") {
                    stubTagListObjsSuccess("Имитация успешной обработки")
                    stubTagValidationBadId("Имитация ошибки валидации id")

                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в tagValidating") { tagValidating = tagRequest.copy() }
                    worker("Очистка id") { tagValidating.id = AppTagId(tagValidating.id.asString().trim()) }

                    validateTagIdNotEmpty("Проверка на непустой id")
                    validateTagIdProperFormat("Проверка формата id")

                    finishTagValidation("Успешное завершение процедуры валидации")
                }
            }
        }.build()
    }
}

