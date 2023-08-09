package site.geniyz.otus.backend.repo.stubs

import site.geniyz.otus.common.models.*
import site.geniyz.otus.common.repo.*
import site.geniyz.otus.stubs.*

class RepoStub() : IRepository {
    override suspend fun createObj(rq: DbObjRequest): DbObjResponse {
        return DbObjResponse(
            data = AppStubObjs.prepareResult {  },
            isSuccess = true,
        )
    }

    override suspend fun readObj(rq: DbObjIdRequest): DbObjResponse {
        return DbObjResponse(
            data = AppStubObjs.prepareResult {  },
            isSuccess = true,
        )
    }

    override suspend fun updateObj(rq: DbObjRequest): DbObjResponse {
        return DbObjResponse(
            data = AppStubObjs.prepareResult {  },
            isSuccess = true,
        )
    }

    override suspend fun deleteObj(rq: DbObjIdRequest): DbObjResponse {
        return DbObjResponse(
            data = AppStubObjs.prepareResult {  },
            isSuccess = true,
        )
    }

    override suspend fun searchObj(rq: DbObjFilterRequest): DbObjsResponse {
        return DbObjsResponse(
            data = AppStubObjs.prepareSearchList(filter = ""),
            isSuccess = true,
        )
    }

    override suspend fun createTag(rq: DbTagRequest): DbTagResponse {
        return DbTagResponse(
            data = AppStubTags.prepareResult {  },
            isSuccess = true,
        )
    }

    override suspend fun readTag(rq: DbTagIdRequest): DbTagResponse {
        return DbTagResponse(
            data = AppStubTags.prepareResult {  },
            isSuccess = true,
        )
    }

    override suspend fun updateTag(rq: DbTagRequest): DbTagResponse {
        return DbTagResponse(
            data = AppStubTags.prepareResult {  },
            isSuccess = true,
        )
    }

    override suspend fun deleteTag(rq: DbTagIdRequest): DbTagResponse {
        return DbTagResponse(
            data = AppStubTags.prepareResult {  },
            isSuccess = true,
        )
    }

    override suspend fun searchTag(rq: DbTagFilterRequest): DbTagsResponse {
        return DbTagsResponse(
            data = AppStubTags.prepareSearchList( filter = ""),
            isSuccess = true,
        )
    }

    override suspend fun searchTags(rq: DbLnkFilterRequest): DbTagsResponse {
        return DbTagsResponse(
            data = AppStubTags.prepareSearchList( filter = ""),
            isSuccess = true,
        )
    }

    override suspend fun searchObjs(rq: DbLnkFilterRequest): DbObjsResponse {
        return DbObjsResponse(
            data = AppStubObjs.prepareSearchList(filter = ""),
            isSuccess = true,
        )
    }

    override suspend fun setTags(rq: DbObjSetTagsRequest): DbTagsResponse {
        return DbTagsResponse(
            data = AppStubTags.prepareSearchList( filter = ""),
            isSuccess = true,
        )
    }
}
