package site.geniyz.otus.api.v1

import site.geniyz.otus.api.v1.models.*

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class ResponseSerializationTest {
    private val testObj = ObjResponseObject(
        name = "Test Title",
        content = "Test content",
        objType = ObjType.TEXT,
    )

    private val objCreateResp: IResponse = ObjCreateResponse(
        responseType = "objCreate",
        requestId = "1234",
        obj = testObj,
    )

    private val objListTagsResp: IResponse = ObjListTagsResponse(
        responseType = "objListTags",
        requestId = "5678",
        obj = testObj,
        tags = mutableListOf(
            TagResponseObject(
                name = "tagNameFirst",
                code = "firstTag",
                id = "1",
            ),
            TagResponseObject(
                name = "tagNameLast",
                code = "lastTag",
                id = "9",
            )
        )
    )

    @Test
    fun serializeObjCreateResp() {
        val json = apiV1Mapper.encodeToString(objCreateResp)
        // println(json)

        assertContains(json, Regex("\"name\":\\s*\"Test Title\""))
        assertContains(json, Regex("\"responseType\":\\s*\"objCreate\""))
    }

    @Test
    fun deserializeObjCreateResp() {
        val json = apiV1Mapper.encodeToString(objCreateResp)
        val obj = apiV1Mapper.decodeFromString(json) as ObjCreateResponse
        assertEquals(objCreateResp, obj)
    }

    @Test
    fun deserializeObjCreateRespNaked() {
        val obj = apiV1Mapper.decodeFromString(
            """{
             "responseType": "objCreate"
            ,"requestId":    "1234"
            ,"result":        null
            ,"errors":        null
            ,"obj": {
                 "name":     "Test Title"
                ,"content":  "Test content"
                ,"objType":  "text"
                ,"id": null
                }
            }"""
        ) as IResponse

        assertEquals("1234", obj.requestId)
        assertEquals(objCreateResp, obj)
    }

    @Test
    fun serializeObjListTagsResp() {
        val json = apiV1Mapper.encodeToString(objListTagsResp)
        // println(json)

        assertContains(json, Regex("\"name\":\\s*\"Test Title\""))
        assertContains(json, Regex("\"responseType\":\\s*\"objListTags\""))
        assertContains(json, Regex(""""tags"\s*:\s*\[\s*\{\s*"name":\s*"tagNameFirst""""))
    }

    @Test
    fun deserializeObjListTagsResp() {
        val json = apiV1Mapper.encodeToString(objListTagsResp)
        val obj = apiV1Mapper.decodeFromString(json) as ObjListTagsResponse
        assertEquals(objListTagsResp, obj)
    }

    @Test
    fun deserializeObjListTagsRespNaked() {
        val obj = apiV1Mapper.decodeFromString(
            """
                {"responseType": "objListTags"
                ,"requestId":    "5678"
                ,"result":        null
                ,"errors":        null
                ,"obj": {
                     "name":    "Test Title"
                    ,"content": "Test content"
                    ,"objType": "text"
                    }
                ,"tags": [
                          {"name": "tagNameFirst", "code": "firstTag", "id":"1"}
                         ,{"name": "tagNameLast",  "code": "lastTag",  "id":"9"}
                         ]
                }
            """.trimIndent()
        ) as IResponse

        assertEquals("5678", obj.requestId)
        assertEquals(objListTagsResp, obj)
    }

}
