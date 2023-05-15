package site.geniyz.otus.api.v1

import site.geniyz.otus.api.v1.models.*

import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule


/**
 * При появлении новых наследников IRequest / IResponse
 *  этот перечень необходимо пополнять
 */
internal val infos = listOf(
    info(ObjCreateRequest::class,    IRequest::class, "objCreate"   ) { copy(requestType = it) },
    info(ObjReadRequest::class,      IRequest::class, "objRead"     ) { copy(requestType = it) },
    info(ObjUpdateRequest::class,    IRequest::class, "objUpdate"   ) { copy(requestType = it) },
    info(ObjDeleteRequest::class,    IRequest::class, "objDelete"   ) { copy(requestType = it) },
    info(ObjSearchRequest::class,    IRequest::class, "objSearch"   ) { copy(requestType = it) },
    info(ObjListTagsRequest::class,  IRequest::class, "objListTags" ) { copy(requestType = it) },
    info(ObjSetTagsRequest::class,   IRequest::class, "objSetTags"  ) { copy(requestType = it) },

    info(ObjCreateResponse::class,   IResponse::class, "objCreate"  ) { copy(responseType = it) },
    info(ObjReadResponse::class,     IResponse::class, "objRread"   ) { copy(responseType = it) },
    info(ObjUpdateResponse::class,   IResponse::class, "objUpdate"  ) { copy(responseType = it) },
    info(ObjDeleteResponse::class,   IResponse::class, "objDelete"  ) { copy(responseType = it) },
    info(ObjSearchResponse::class,   IResponse::class, "objSearch"  ) { copy(responseType = it) },
    info(ObjListTagsResponse::class, IResponse::class, "objListTags") { copy(responseType = it) },
    info(ObjSetTagsResponse::class,  IResponse::class, "objSetTags" ) { copy(responseType = it) },

    info(TagDeleteRequest::class,    IRequest::class, "tagDelete"   ) { copy(requestType = it) },
    info(TagSearchRequest::class,    IRequest::class, "tagSearch"   ) { copy(requestType = it) },
    info(TagListObjsRequest::class,  IRequest::class, "tagListObjs" ) { copy(requestType = it) },

    info(TagDeleteResponse::class,   IResponse::class, "tagDelete"  ) { copy(responseType = it) },
    info(TagSearchResponse::class,   IResponse::class, "tagSearch"  ) { copy(responseType = it) },
    info(TagListObjsResponse::class, IResponse::class, "tagListObjs") { copy(responseType = it) },
)

val apiV1Mapper = Json {
    classDiscriminator = "_"
    encodeDefaults = true
    ignoreUnknownKeys = true

    serializersModule = SerializersModule {
        setupPolymorphic()
    }
}
