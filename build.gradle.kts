import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
}

val jvmTarget = "11"

group = "site.geniyz.otus"
version = "0.0.1-SNAPSHOT"

allprojects {
    repositories {
        mavenLocal()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

subprojects {
    group = rootProject.group
    version = rootProject.version

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = jvmTarget
        java.targetCompatibility = JavaVersion.VERSION_11
    }
}
