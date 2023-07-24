package site.geniyz.otus.common.repo

import site.geniyz.otus.common.models.AppError

interface IDbResponse<T> {
    val data: T?
    val isSuccess: Boolean
    val errors: List<AppError>
}
