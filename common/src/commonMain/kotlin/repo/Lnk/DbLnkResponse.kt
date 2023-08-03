package site.geniyz.otus.common.repo

import site.geniyz.otus.common.helpers.errorEmptyId as appErrorEmptyId
import site.geniyz.otus.common.helpers.errorNotFound as appErrorNotFound
import site.geniyz.otus.common.helpers.errorRepoConcurrency
import site.geniyz.otus.common.models.*

data class DbLnkResponse(
    override val data: Pair<AppObj, AppTag>?,
    override val isSuccess: Boolean,
    override val errors: List<AppError> = emptyList()
): IDbResponse<Pair<AppObj, AppTag>> {

    companion object {
        val MOCK_SUCCESS_EMPTY = DbLnkResponse(null, true)
        fun success(result: Pair<AppObj, AppTag>) = DbLnkResponse(result, true)
        fun error(errors: List<AppError>, data: Pair<AppObj, AppTag>? = null) = DbLnkResponse(data, false, errors)
        fun error(error: AppError, data: Pair<AppObj, AppTag>? = null) = DbLnkResponse(data, false, listOf(error))

        val errorEmptyId = error(appErrorEmptyId)

        /*
        fun errorConcurrent(lock: AppLock, data: Pair<AppObj, AppTag>?) = error(
            errorRepoConcurrency(lock, data?.lock?.let { AppLock(it.asString()) }),
            data
        )
        */

        val errorNotFound = error(appErrorNotFound)
    }
}
