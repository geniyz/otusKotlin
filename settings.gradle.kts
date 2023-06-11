rootProject.name = "otus"

pluginManagement {
    val kotlinVersion:  String by settings
    val openapiVersion: String by settings
    val ktorVersion:    String by settings

    plugins {
        kotlin("jvm") version kotlinVersion apply false
        kotlin("plugin.serialization") version kotlinVersion apply false
        id("org.openapi.generator") version openapiVersion apply false
        id("io.ktor.plugin") version ktorVersion apply false
    }
}

include(
    "api-kmp",         // сериализация транстпортных моделей
    "common",          // внутрянка (внутренние модели, хелперы, etc.)
    "mappers",         // преобразование внутренних моделей в транспортные и наоборот
    "stubs",           // заглушки
    "biz",             // бизнес-логика
    "tagger-app-ktor", // собсно приложение http и ws
    )
