package site.geniyz.otus.auth

import io.ktor.client.request.*
import io.ktor.server.testing.*
import org.junit.Test
import site.geniyz.otus.app.auth.AuthConfig
import site.geniyz.otus.app.auth.addAuth
import site.geniyz.otus.app.helpers.testSettings
import site.geniyz.otus.app.moduleJvm
import kotlin.test.assertEquals

class AuthTest {
    @Test
    fun invalidAudience() = testApplication {
        application {
            moduleJvm(testSettings())
        }

        val response = client.post(urlString = "/v1/obj/create") {
            addAuth(config = AuthConfig.TEST.copy(audience = "invalid"))
        }
        assertEquals(401, response.status.value)
    }
}
