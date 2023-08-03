package site.geniyz.otus.backend.repo.tests

import site.geniyz.otus.common.repo.*

class RepositoryMock(
    private val invokeCreateObj: (DbObjRequest)       -> DbObjResponse  = { DbObjResponse.MOCK_SUCCESS_EMPTY  },
    private val invokeReadObj:   (DbObjIdRequest)     -> DbObjResponse  = { DbObjResponse.MOCK_SUCCESS_EMPTY  },
    private val invokeUpdateObj: (DbObjRequest)       -> DbObjResponse  = { DbObjResponse.MOCK_SUCCESS_EMPTY  },
    private val invokeDeleteObj: (DbObjIdRequest)     -> DbObjResponse  = { DbObjResponse.MOCK_SUCCESS_EMPTY  },
    private val invokeSearchObj: (DbObjFilterRequest) -> DbObjsResponse = { DbObjsResponse.MOCK_SUCCESS_EMPTY },

    private val invokeCreateTag: (DbTagRequest)       -> DbTagResponse  = { DbTagResponse.MOCK_SUCCESS_EMPTY  },
    private val invokeReadTag:   (DbTagIdRequest)     -> DbTagResponse  = { DbTagResponse.MOCK_SUCCESS_EMPTY  },
    private val invokeUpdateTag: (DbTagRequest)       -> DbTagResponse  = { DbTagResponse.MOCK_SUCCESS_EMPTY  },
    private val invokeDeleteTag: (DbTagIdRequest)     -> DbTagResponse  = { DbTagResponse.MOCK_SUCCESS_EMPTY  },
    private val invokeSearchTag: (DbTagFilterRequest) -> DbTagsResponse = { DbTagsResponse.MOCK_SUCCESS_EMPTY },

    // private val invokeCreateLnk: (DbLnkRequest)       -> DbLnkResponse  = { DbLnkResponse.MOCK_SUCCESS_EMPTY  },
    // private val invokeDeleteLnk: (DbLnkRequest)       -> DbLnkResponse  = { DbLnkResponse.MOCK_SUCCESS_EMPTY  },
    // private val invokeSearchLnk: (DbLnkFilterRequest) -> DbLnksResponse = { DbLnksResponse.MOCK_SUCCESS_EMPTY },

    private val invokeSearchTags: (DbLnkFilterRequest) -> DbTagsResponse = { DbTagsResponse.MOCK_SUCCESS_EMPTY },
    private val invokeSearchObjs: (DbLnkFilterRequest) -> DbObjsResponse = { DbObjsResponse.MOCK_SUCCESS_EMPTY },
    private val invokeSetTags:    (DbObjSetTagsRequest)-> DbTagsResponse = { DbTagsResponse.MOCK_SUCCESS_EMPTY },
    ): IRepository {
    override suspend fun createObj(rq: DbObjRequest): DbObjResponse {
        return invokeCreateObj(rq)
    }

    override suspend fun readObj(rq: DbObjIdRequest): DbObjResponse {
        return invokeReadObj(rq)
    }

    override suspend fun updateObj(rq: DbObjRequest): DbObjResponse {
        return invokeUpdateObj(rq)
    }

    override suspend fun deleteObj(rq: DbObjIdRequest): DbObjResponse {
        return invokeDeleteObj(rq)
    }

    override suspend fun searchObj(rq: DbObjFilterRequest): DbObjsResponse {
        return invokeSearchObj(rq)
    }


    override suspend fun createTag(rq: DbTagRequest): DbTagResponse {
        return invokeCreateTag(rq)
    }

    override suspend fun readTag(rq: DbTagIdRequest): DbTagResponse {
        return invokeReadTag(rq)
    }

    override suspend fun updateTag(rq: DbTagRequest): DbTagResponse {
        return invokeUpdateTag(rq)
    }

    override suspend fun deleteTag(rq: DbTagIdRequest): DbTagResponse {
        return invokeDeleteTag(rq)
    }

    override suspend fun searchTag(rq: DbTagFilterRequest): DbTagsResponse {
        return invokeSearchTag(rq)
    }

    /*
    override suspend fun createLnk(rq: DbLnkRequest): DbLnkResponse {
        return invokeCreateLnk(rq)
    }

    override suspend fun deleteLnk(rq: DbLnkRequest): DbLnkResponse {
        return invokeDeleteLnk(rq)
    }

    override suspend fun searchLnk(rq: DbLnkFilterRequest): DbLnksResponse {
        return invokeSearchLnk(rq)
    }
    */

    override suspend fun searchTags(rq: DbLnkFilterRequest): DbTagsResponse {
        return invokeSearchTags(rq)
    }

    override suspend fun searchObjs(rq: DbLnkFilterRequest): DbObjsResponse {
        return invokeSearchObjs(rq)
    }
    override suspend fun setTags(rq: DbObjSetTagsRequest): DbTagsResponse {
        return invokeSetTags(rq)
    }
}
