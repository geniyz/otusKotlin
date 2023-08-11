package site.geniyz.otus.common.helpers

import site.geniyz.otus.common.AppContext
import site.geniyz.otus.common.exceptions.RepoConcurrencyException
import site.geniyz.otus.common.models.*

fun Throwable.asAppError(
    code: String = "unknown",
    group: String = "exceptions",
    message: String = this.message ?: "",
) = AppError(
    code = code,
    group = group,
    field = "",
    message = message,
    exception = this,
)

fun AppContext.addError(vararg error: AppError) = errors.addAll(error)

fun AppContext.fail(error: AppError) {
    addError(error)
    state = AppState.FAILING
}

fun errorValidation(
    field: String,
    /**
     * Код, характеризующий ошибку. Не должен включать имя поля или указание на валидацию.
     * Например: empty, badSymbols, tooLong, etc
     */
    violationCode: String,
    description: String,
    level: AppError.Level = AppError.Level.ERROR,
    segment: String = ""
) = AppError(
    code    = "validation-$field-$violationCode",
    field   = field,
    group   = "validation" + if(segment.isNotBlank()){"-$segment"}else{""},
    message = "Validation error for field $field: $description",
    level   = level,
)


fun errorRepoConcurrency(
    expectedLock: AppLock,
    actualLock: AppLock?,
    exception: Exception? = null,
) = AppError(
    field = "lock",
    code = "concurrency",
    group = "repo",
    message = "The object has been changed concurrently by another user or process",
    exception = exception ?: RepoConcurrencyException(expectedLock, actualLock),
)

val errorNotFound = AppError(
    field = "id",
    message = "Нет подходящих данных",
    code = "not-found"
)

val errorEmptyId = AppError(
    field = "id",
    message = "Идентификатор должен быть не пустым"
)

fun errorAdministration(
    field: String = "",
    violationCode: String,
    description: String,
    exception: Exception? = null,
    level: AppError.Level = AppError.Level.ERROR,
) = AppError(
    field = field,
    code = "administration-$violationCode",
    group = "administration",
    message = "Microservice management error: $description",
    level = level,
    exception = exception,
)

fun errorRepoUnique(
    fields: List<String> = emptyList(),
    exception: Exception? = null,
) = AppError(
    field = fields.joinToString(),
    code = "unique",
    group = "repo",
    message = "Нарушение уникатьности полей $fields",
    exception = exception,
)