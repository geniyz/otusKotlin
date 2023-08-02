package site.geniyz.otus.biz

import site.geniyz.otus.biz.general.prepareResult
import site.geniyz.otus.biz.groups.*
import site.geniyz.otus.biz.repo.*
import site.geniyz.otus.biz.workers.*

import site.geniyz.otus.common.AppContext
import site.geniyz.otus.common.models.*

import site.geniyz.otus.cor.*
import site.geniyz.otus.biz.validation.*

val TagBusinessChain: ICorExec<AppContext>
    get() = rootChain<AppContext> {
                operation("Удалить метку", AppCommand.TAG_DELETE) {
                    stubs("Обработка стабов") {
                        stubTagDeleteSuccess("Имитация успешной обработки")
                        stubTagValidationBadId("Имитация ошибки валидации id")

                        stubDbError("Имитация ошибки работы с БД")
                        stubNoCase("Ошибка: запрошенный стаб недопустим")
                    }
                    validation {
                        worker("Копируем поля в tagValidating") { tagValidating = tagRequest.copy() }
                        worker("Очистка id") { tagValidating.id = AppTagId(tagValidating.id.asString().trim()) }
                        worker("Очистка lock") { tagValidating.lock = AppLock(tagValidating.lock.asString().trim()) }

                        validateTagIdNotEmpty("Проверка на непустой id")
                        validateTagIdProperFormat("Проверка формата id")
                        validateLockNotEmpty("Проверка на непустой lock")
                        validateLockProperFormat("Проверка формата lock")

                        finishTagValidation("Успешное завершение процедуры валидации")
                    }
                    chain {
                        title = "Логика удалдения"
                        repoTagPrepareDelete("Подготовка к удалению метки")
                        repoTagDelete("Удаление метки из БД")
                    }
                    prepareResult("Подготовка ответа")
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
                    chain {
                        title = "Логика поиска"
                        repoTagSearch("Поиск меток в БД")
                    }
                    prepareResult("Подготовка ответа")

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
                    chain {
                        title = "Логика получения перечня объектов с указанной меткой"
                        repoTagListObjs("Получение объектов в БД")
                    }
                    prepareResult("Подготовка ответа")
                }
            }.build()