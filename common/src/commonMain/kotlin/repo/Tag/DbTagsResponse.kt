package site.geniyz.otus.common.repo

import site.geniyz.otus.common.models.*

data class DbTagsResponse(
    override val data: List<AppTag>?,
    override val isSuccess: Boolean,
    override val errors: List<AppError> = emptyList(),
): IDbResponse<List<AppTag>> {

    companion object {
        val MOCK_SUCCESS_EMPTY = DbTagsResponse(emptyList(), true)
        fun success(result: List<AppTag>) = DbTagsResponse(result, true)
        fun error(errors: List<AppError>) = DbTagsResponse(null, false, errors)
        fun error(error: AppError) = DbTagsResponse(null, false, listOf(error))
    }
}
