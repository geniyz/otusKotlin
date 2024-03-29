package site.geniyz.otus.api.v1

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
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
    info(ObjReadResponse::class,     IResponse::class, "objRead"    ) { copy(responseType = it) },
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

    info(ObjInitResponse::class,     IResponse::class, "init"       ) { copy(responseType = it) }, // TODO: я до конца не понимаю пока что это

)

val apiV1Mapper = Json {
    classDiscriminator = "_"
    encodeDefaults = true
    ignoreUnknownKeys = true

    serializersModule = SerializersModule {
        setupPolymorphic()
    }
}

fun apiV1RequestSerialize(request: IRequest): String = apiV1Mapper.encodeToString(request)

@Suppress("UNCHECKED_CAST")
fun <T : IRequest> apiV1RequestDeserialize(json: String): T = apiV1Mapper.decodeFromString<IRequest>(json) as T

fun apiV1ResponseSerialize(response: IResponse): String = apiV1Mapper.encodeToString(response)

@Suppress("UNCHECKED_CAST")
fun <T : IResponse> apiV1ResponseDeserialize(json: String): T = apiV1Mapper.decodeFromString<IResponse>(json) as T
