package site.geniyz.otus.biz

import site.geniyz.otus.biz.general.*
import site.geniyz.otus.biz.repo.*
import site.geniyz.otus.biz.groups.*
import site.geniyz.otus.biz.permissions.*
import site.geniyz.otus.biz.workers.*

import site.geniyz.otus.common.AppContext
import site.geniyz.otus.common.models.*

import site.geniyz.otus.cor.*

import site.geniyz.otus.biz.validation.*

val ObjBusinessChain: ICorExec<AppContext>
    get() = rootChain<AppContext> {
        initStatus("Инициализация статуса")
        initRepo("Инициализация репозитория")

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

                    chainPermissions("Вычисление разрешений для пользователя")
                    chain {
                        title = "Логика сохранения"
                        repoObjPrepareCreate("Подготовка объекта для сохранения")
                        accessValidation("Вычисление прав доступа")
                        repoObjCreate("Создание объекта в БД")
                    }
                    frontPermissions("Вычисление пользовательских разрешений для фронтенда")
                    prepareResult("Подготовка ответа")
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
                    chainPermissions("Вычисление разрешений для пользователя")
                    chain {
                        title = "Логика считывания данных объекта"
                        repoObjRead("Получение объекта в БД")
                        accessValidation("Вычисление прав доступа")
                        worker {
                            title = "Подготовка ответа для Read"
                            on { state == AppState.RUNNING }
                            handle { objRepoDone = objRepoRead }
                        }
                    }
                    frontPermissions("Вычисление пользовательских разрешений для фронтенда")
                    prepareResult("Подготовка ответа")
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
                        worker("Очистка id") { objValidating.id = AppObjId(objValidating.id.asString().trim()) }
                        worker("Очистка lock") { objValidating.lock = AppLock(objValidating.lock.asString().trim()) }
                        worker("Очистка заголовка") { objValidating.name = objValidating.name.trim() }
                        worker("Очистка описания") { objValidating.content = objValidating.content.trim() }

                        validateObjIdNotEmpty("Проверка на непустой id")
                        validateObjIdProperFormat("Проверка формата id")
                        validateLockNotEmpty("Проверка на непустой lock")
                        validateLockProperFormat("Проверка формата lock")
                        validateObjNameNotEmpty("Проверка, что заголовок не пуст")
                        validateObjNameHasContent("Проверка символов")
                        validateObjContentNotEmpty("Проверка, что описание не пусто")
                        validateObjContentHasContent("Проверка символов")

                        finishObjValidation("Завершение проверок")
                    }
                    chainPermissions("Вычисление разрешений для пользователя")
                    chain {
                        title = "Логика корректировки данных"
                        repoObjRead("Чтение объявления из БД")
                        accessValidation("Вычисление прав доступа")
                        repoObjPrepareUpdate("Подготовка к корректировке данных объекта в БД")
                        repoObjUpdate ("Изменение объекта в БД")
                    }
                    prepareResult("Подготовка ответа")
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
                        worker("Очистка lock") { objValidating.lock = AppLock(objValidating.lock.asString().trim()) }

                        validateObjIdNotEmpty("Проверка на непустой id")
                        validateObjIdProperFormat("Проверка формата id")
                        validateLockNotEmpty("Проверка на непустой lock")
                        validateLockProperFormat("Проверка формата lock")

                        finishObjValidation("Успешное завершение процедуры валидации")
                    }
                    chainPermissions("Вычисление разрешений для пользователя")
                    chain {
                        title = "Логика Удаления данных"
                        repoObjRead("Чтение объявления из БД")
                        accessValidation("Вычисление прав доступа")
                        repoObjPrepareDelete("Подготовка к удалению объекта из БД")
                        repoObjDelete("Удаление объекта из БД")
                    }
                    frontPermissions("Вычисление пользовательских разрешений для фронтенда")
                    prepareResult("Подготовка ответа")
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
                    chainPermissions("Вычисление разрешений для пользователя")
                    chain {
                        title = "Логика поиска объектов"
                        searchTypes("Подготовка поискового запроса")
                        repoObjSearch("Поиск объектов из БД")
                    }
                    frontPermissions("Вычисление пользовательских разрешений для фронтенда")
                    prepareResult("Подготовка ответа")

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
                    chainPermissions("Вычисление разрешений для пользователя")
                    chain {
                        title = "Логика получения меток объенкта"
                        repoObjRead("Получение объекта в БД")
                        accessValidation("Вычисление прав доступа")
                        repoObjListTags("Получение меток объекта из БД")
                    }
                    frontPermissions("Вычисление пользовательских разрешений для фронтенда")
                    prepareResult("Подготовка ответа")
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
                        worker("Очистка lock") { objValidating.lock = AppLock(objValidating.lock.asString().trim()) }

                        validateObjIdNotEmpty("Проверка на непустой id")
                        validateObjIdProperFormat("Проверка формата id")
                        validateLockNotEmpty("Проверка на непустой lock")
                        validateLockProperFormat("Проверка формата lock")

                        finishObjValidation("Успешное завершение процедуры валидации")
                    }
                    chainPermissions("Вычисление разрешений для пользователя")
                    chain {
                        title = "Логика изменения меток объенкта"
                        repoObjRead("Получение объекта в БД")
                        accessValidation("Вычисление прав доступа")
                        repoObjSetTags("Изменение меток объекта из БД")
                    }
                    frontPermissions("Вычисление пользовательских разрешений для фронтенда")
                    prepareResult("Подготовка ответа")
                }
            }.build()
