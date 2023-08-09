package site.geniyz.otus.app.auth

import io.ktor.client.request.*

expect fun HttpRequestBuilder.addAuth(
    id: String = "user1",
    groups: List<String> = listOf("USER", "TEST"),
    config: AuthConfig = AuthConfig.TEST,
)
