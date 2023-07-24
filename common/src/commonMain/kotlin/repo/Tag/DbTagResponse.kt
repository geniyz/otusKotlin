package site.geniyz.otus.common.repo

import site.geniyz.otus.common.helpers.errorEmptyId as appErrorEmptyId
import site.geniyz.otus.common.helpers.errorNotFound as appErrorNotFound
import site.geniyz.otus.common.helpers.errorRepoConcurrency
import site.geniyz.otus.common.models.*

data class DbTagResponse(
    override val data: AppTag?,
    override val isSuccess: Boolean,
    override val errors: List<AppError> = emptyList()
): IDbResponse<AppTag> {

    companion object {
        val MOCK_SUCCESS_EMPTY = DbTagResponse(null, true)
        fun success(result: AppTag) = DbTagResponse(result, true)
        fun error(errors: List<AppError>, data: AppTag? = null) = DbTagResponse(data, false, errors)
        fun error(error: AppError, data: AppTag? = null) = DbTagResponse(data, false, listOf(error))

        val errorEmptyId = error(appErrorEmptyId)

        fun errorConcurrent(lock: AppLock, tag: AppTag?) = error(
            errorRepoConcurrency(lock, tag?.lock?.let { AppLock(it.asString()) }),
            tag
        )

        val errorNotFound = error(appErrorNotFound)
    }
}
