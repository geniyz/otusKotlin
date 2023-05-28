@file:Suppress("UNUSED_VARIABLE")

import org.jetbrains.kotlin.util.suffixIfNot

val ktorVersion: String by project
val logbackVersion: String by project
val serializationVersion: String by project

fun ktor(module: String, prefix: String = "server-", version: String? = this@Build_gradle.ktorVersion): Any =
    "io.ktor:ktor-${prefix.suffixIfNot("-")}$module:$version"

plugins {
    id("application")
    kotlin("plugin.serialization")
    kotlin("multiplatform")
    id("io.ktor.plugin")
}

repositories {
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
}

application {
    mainClass.set("io.ktor.server.cio.EngineMain")
}

ktor {
    docker {
        localImageName.set(project.name + "-ktor")
        imageTag.set(project.version.toString())
        jreVersion.set(io.ktor.plugin.features.JreVersion.JRE_11)
    }
}

jib {
    container.mainClass = "io.ktor.server.cio.EngineMain"
}

kotlin {
    jvm {
        withJava()
    }
    linuxX64 {}
    // macosX64 {}
    // macosArm64 {}

    targets.withType<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget> {
        binaries {
            executable {
                entryPoint = "site.geniyz.otus.app.main"
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation(ktor("core")) // "io.ktor:ktor-server-core:$ktorVersion"

                implementation(ktor("core")) // "io.ktor:ktor-server-core:$ktorVersion"
                implementation(ktor("cio")) // "io.ktor:ktor-server-cio:$ktorVersion"
                implementation(ktor("auth")) // "io.ktor:ktor-server-auth:$ktorVersion"
                implementation(ktor("auto-head-response")) // "io.ktor:ktor-server-auto-head-response:$ktorVersion"
                implementation(ktor("caching-headers")) // "io.ktor:ktor-server-caching-headers:$ktorVersion"
                implementation(ktor("cors")) // "io.ktor:ktor-server-cors:$ktorVersion"
                implementation(ktor("websockets")) // "io.ktor:ktor-server-websockets:$ktorVersion"
                implementation(ktor("config-yaml")) // "io.ktor:ktor-server-config-yaml:$ktorVersion"
                implementation(ktor("content-negotiation")) // "io.ktor:ktor-server-content-negotiation:$ktorVersion"
                implementation(ktor("websockets")) // "io.ktor:ktor-websockets:$ktorVersion"
                implementation(ktor("auth")) // "io.ktor:ktor-auth:$ktorVersion"

                implementation(project(":common"))
                implementation(project(":biz"))
                implementation(project(":api-kmp"))
                implementation(project(":mappers"))
                implementation(project(":stubs"))

                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:$serializationVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))

                implementation(ktor("test-host"))
                implementation(ktor("content-negotiation", prefix = "client-"))
                implementation(ktor("websockets", prefix = "client-"))
            }
        }

    }
}

tasks.withType<Copy> {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}
