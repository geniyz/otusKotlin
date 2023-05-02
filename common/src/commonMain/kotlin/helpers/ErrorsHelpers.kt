package site.geniyz.otus.common.helpers

import site.geniyz.otus.common.AppContext
import site.geniyz.otus.common.models.AppError

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
