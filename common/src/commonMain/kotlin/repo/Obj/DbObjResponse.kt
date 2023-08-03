package site.geniyz.otus.common.repo

import site.geniyz.otus.common.helpers.errorEmptyId as appErrorEmptyId
import site.geniyz.otus.common.helpers.errorNotFound as appErrorNotFound
import site.geniyz.otus.common.helpers.errorRepoConcurrency
import site.geniyz.otus.common.models.*

data class DbObjResponse(
    override val data: AppObj?,
    override val isSuccess: Boolean,
    override val errors: List<AppError> = emptyList()
): IDbResponse<AppObj> {

    companion object {
        val MOCK_SUCCESS_EMPTY = DbObjResponse(null, true)
        fun success(result: AppObj) = DbObjResponse(result, true)
        fun error(errors: List<AppError>, data: AppObj? = null) = DbObjResponse(data, false, errors)
        fun error(error: AppError, data: AppObj? = null) = DbObjResponse(data, false, listOf(error))

        val errorEmptyId = error(appErrorEmptyId)

        fun errorConcurrent(lock: AppLock, obj: AppObj?) = error(
            errorRepoConcurrency(lock, obj?.lock?.let { AppLock(it.asString()) }),
            obj
        )

        val errorNotFound = error(appErrorNotFound)
    }
}
