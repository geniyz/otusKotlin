plugins {
    kotlin("jvm")
}

dependencies {
    val exposedVersion: String by project
    val postgresDriverVersion: String by project
    val kmpUUIDVersion: String by project
    val testcontainersVersion: String by project

    implementation(kotlin("stdlib"))

    implementation(project(":common"))

    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-kotlin-datetime:$exposedVersion")
    implementation("com.benasher44:uuid:$kmpUUIDVersion")

    implementation("org.postgresql:postgresql:$postgresDriverVersion")
    testImplementation("org.testcontainers:postgresql:$testcontainersVersion")

    testImplementation(project(":repo-tests"))
}
