package site.geniyz.otus.common.repo

import site.geniyz.otus.common.models.*

data class DbObjsResponse(
    override val data: List<AppObj>?,
    override val isSuccess: Boolean,
    override val errors: List<AppError> = emptyList(),
): IDbResponse<List<AppObj>> {

    companion object {
        val MOCK_SUCCESS_EMPTY = DbObjsResponse(emptyList(), true)
        fun success(result: List<AppObj>) = DbObjsResponse(result, true)
        fun error(errors: List<AppError>) = DbObjsResponse(null, false, errors)
        fun error(error: AppError) = DbObjsResponse(null, false, listOf(error))
    }
}
