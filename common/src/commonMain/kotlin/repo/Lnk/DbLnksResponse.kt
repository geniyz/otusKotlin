package site.geniyz.otus.common.repo

import site.geniyz.otus.common.models.*

data class DbLnksResponse(
    override val data: List<Pair<AppObjId, AppTagId>>?,
    override val isSuccess: Boolean,
    override val errors: List<AppError> = emptyList(),
): IDbResponse<List<Pair<AppObjId, AppTagId>>> {

    companion object {
        val MOCK_SUCCESS_EMPTY = DbLnksResponse(emptyList(), true)
        fun success(result: List<Pair<AppObjId, AppTagId>> ) = DbLnksResponse(result, true)
        fun error(errors: List<AppError>) = DbLnksResponse(null, false, errors)
        fun error(error: AppError) = DbLnksResponse(null, false, listOf(error))
    }
}
