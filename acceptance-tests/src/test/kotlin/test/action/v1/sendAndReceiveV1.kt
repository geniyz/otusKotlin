package site.geniyz.otus.blackbox.test.action.v1

import mu.KotlinLogging

import site.geniyz.otus.api.v1.apiV1RequestSerialize
import site.geniyz.otus.api.v1.apiV1ResponseDeserialize
import site.geniyz.otus.api.v1.models.IRequest
import site.geniyz.otus.api.v1.models.IResponse
import site.geniyz.otus.blackbox.fixture.client.Client

private val log = KotlinLogging.logger {}

suspend fun Client.sendAndReceive(path: String, request: IRequest): IResponse {
    val requestBody = apiV1RequestSerialize(request)
    log.info { "Send to v1/$path\n$requestBody" }

    val responseBody = sendAndReceive("v1", path, requestBody)
    log.info { "Received\n$responseBody" }

    return apiV1ResponseDeserialize(responseBody)
}