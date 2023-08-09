package site.geniyz.otus.app.auth

import io.ktor.client.request.*

actual fun HttpRequestBuilder.addAuth(
    id: String,
    groups: List<String>,
    config: AuthConfig
) {
}