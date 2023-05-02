rootProject.name = "otus"

pluginManagement {
    val kotlinVersion: String by settings
    val openapiVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion apply false
        kotlin("plugin.serialization") version kotlinVersion apply false
        id("org.openapi.generator") version openapiVersion apply false

    }
}

// include("hw01-helloWorld")
include(
    "api-kmp",         // сериализация транстпортных моделей
    "common",          // внутрянка (внутренние модели, хелперы, etc.)
    )
