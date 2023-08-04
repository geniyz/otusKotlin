package site.geniyz.otus.app

import com.auth0.jwt.JWT
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.util.*
import site.geniyz.otus.api.v1.apiV1Mapper
import site.geniyz.otus.app.auth.AuthConfig.Companion.GROUPS_CLAIM
import site.geniyz.otus.app.auth.resolveAlgorithm
import site.geniyz.otus.app.plugins.initAppSettings
import site.geniyz.otus.app.v1.WSController
import site.geniyz.otus.app.v1.v1Objs
import site.geniyz.otus.app.v1.v1Tags

import site.geniyz.otus.app.module as commonModule

private val clazz = Application::moduleJvm::class.qualifiedName ?: "Application"

@Suppress("unused")
fun Application.moduleJvm(appSettings: AppSettings = initAppSettings()) {

    commonModule(appSettings)

    install(Authentication) {

        jwt {
            val authConfig = appSettings.auth
            realm = authConfig.realm

            verifier {
                val algorithm = it.resolveAlgorithm(authConfig)
                JWT
                    .require(algorithm)
                    .withAudience(authConfig.audience)
                    .withIssuer(authConfig.issuer)
                    .build()
            }
            validate { jwtCredential: JWTCredential ->
                when {
                    jwtCredential.payload.getClaim(GROUPS_CLAIM).asList(String::class.java).isNullOrEmpty() -> {
                        this@moduleJvm.log.error("Groups claim must not be empty in JWT token")
                        null
                    }

                    else -> JWTPrincipal(jwtCredential.payload)
                }
            }
        }
    }

    val ws = WSController()

    routing {
        get("/") {
            call.respondText("Приложение для работы с метками")
        }
        route("v1") {
            pluginRegistry.getOrNull(AttributeKey("ContentNegotiation"))?:install(ContentNegotiation) {
                json(apiV1Mapper)
            }
            authenticate {
                v1Objs(appSettings)
                v1Tags(appSettings)
            }
        }

        webSocket("/ws/v1") {
            ws.handle(this, appSettings)
        }
    }
}

fun main(args: Array<String>): Unit = io.ktor.server.cio.EngineMain.main(args)
