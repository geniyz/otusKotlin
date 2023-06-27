package site.geniyz.otus.biz

import site.geniyz.otus.biz.groups.*
import site.geniyz.otus.biz.workers.*

import site.geniyz.otus.common.AppContext
import site.geniyz.otus.common.models.*

import site.geniyz.otus.cor.*

import site.geniyz.otus.biz.validation.*

val ObjBusinessChain: ICorExec<AppContext>
    get() = rootChain<AppContext> {
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
