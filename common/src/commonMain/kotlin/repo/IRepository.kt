package site.geniyz.otus.common.repo

interface IRepository {
    suspend fun createObj(rq: DbObjRequest):        DbObjResponse
    suspend fun readObj  (rq: DbObjIdRequest):      DbObjResponse
    suspend fun updateObj(rq: DbObjRequest):        DbObjResponse
    suspend fun deleteObj(rq: DbObjIdRequest):      DbObjResponse
    suspend fun searchObj(rq: DbObjFilterRequest):  DbObjsResponse

    suspend fun createTag(rq: DbTagRequest):        DbTagResponse
    suspend fun readTag  (rq: DbTagIdRequest):      DbTagResponse
    suspend fun updateTag(rq: DbTagRequest):        DbTagResponse
    suspend fun deleteTag(rq: DbTagIdRequest):      DbTagResponse
    suspend fun searchTag(rq: DbTagFilterRequest):  DbTagsResponse

    // suspend fun createLnk(rq: DbLnkRequest):        DbLnkResponse
    // suspend fun deleteLnk(rq: DbLnkRequest):        DbLnkResponse
    // suspend fun searchLnk(rq: DbLnkFilterRequest):  DbLnksResponse

    suspend fun searchTags(rq: DbLnkFilterRequest): DbTagsResponse
    suspend fun searchObjs(rq: DbLnkFilterRequest): DbObjsResponse
    suspend fun setTags   (rq: DbObjSetTagsRequest):DbTagsResponse

    companion object {
        val NONE = object : IRepository {
            override suspend fun createObj(rq: DbObjRequest): DbObjResponse {
                TODO("Not yet implemented")
            }

            override suspend fun readObj(rq: DbObjIdRequest): DbObjResponse {
                TODO("Not yet implemented")
            }

            override suspend fun updateObj(rq: DbObjRequest): DbObjResponse {
                TODO("Not yet implemented")
            }

            override suspend fun deleteObj(rq: DbObjIdRequest): DbObjResponse {
                TODO("Not yet implemented")
            }

            override suspend fun searchObj(rq: DbObjFilterRequest): DbObjsResponse {
                TODO("Not yet implemented")
            }

            override suspend fun createTag(rq: DbTagRequest): DbTagResponse {
                TODO("Not yet implemented")
            }

            override suspend fun readTag(rq: DbTagIdRequest): DbTagResponse {
                TODO("Not yet implemented")
            }

            override suspend fun updateTag(rq: DbTagRequest): DbTagResponse {
                TODO("Not yet implemented")
            }

            override suspend fun deleteTag(rq: DbTagIdRequest): DbTagResponse {
                TODO("Not yet implemented")
            }

            override suspend fun searchTag(rq: DbTagFilterRequest): DbTagsResponse {
                TODO("Not yet implemented")
            }

            /*
            override suspend fun createLnk(rq: DbLnkRequest): DbLnkResponse {
                TODO("Not yet implemented")
            }

            override suspend fun deleteLnk(rq: DbLnkRequest): DbLnkResponse {
                TODO("Not yet implemented")
            }

            override suspend fun searchLnk(rq: DbLnkFilterRequest): DbLnksResponse {
                TODO("Not yet implemented")
            }
            */

            override suspend fun searchTags(rq: DbLnkFilterRequest): DbTagsResponse {
                TODO("Not yet implemented")
            }

            override suspend fun searchObjs(rq: DbLnkFilterRequest): DbObjsResponse {
                TODO("Not yet implemented")
            }

            override suspend fun setTags(rq: DbObjSetTagsRequest): DbTagsResponse {
                TODO("Not yet implemented")
            }
        }
    }

}
