package site.geniyz.otus.common.helpers

import site.geniyz.otus.common.AppContext
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
