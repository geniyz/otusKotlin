package site.geniyz.otus.app

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {
    @Test
    fun `root endpoint`() = testApplication {
        val response = client.get("/")
        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals("Приложение для работы с метками", response.bodyAsText())
    }
}
