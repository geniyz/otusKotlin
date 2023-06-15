package site.geniyz.otus.blackbox.fixture.client

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import site.geniyz.otus.blackbox.fixture.docker.DockerCompose

/**
 * Отправка запросов по http/rest
 */
class RestClient(dockerCompose: DockerCompose) : Client {
    private val urlBuilder by lazy { dockerCompose.inputUrl }
    private val client = HttpClient()
    override suspend fun sendAndReceive(version: String, path: String, request: String): String {
        val url = urlBuilder.apply {
            path("$version/$path")
        }.build()

        val resp = client.post {
            url(url)
            headers {
                append(HttpHeaders.ContentType, ContentType.Application.Json)
            }
            accept(ContentType.Application.Json)
            setBody(request)
        }.call

        return resp.body()
    }
}
