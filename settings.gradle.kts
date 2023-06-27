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
    "acceptance-tests",// приёмочные тесты докером

    "lib-log-common", "lib-log-kermit", "lib-log-logback",     // логгирование
    "lib-log-api",                                             // сериализация моделей логгирования
    "lib-log-mappers",                                         // преобразования в модели логгировыания

    "lib-cor",         // Chain of Responsibility 
    )
