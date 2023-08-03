package site.geniyz.otus.backend.repo.sql

open class SqlProperties(
    val url: String = "jdbc:postgresql://localhost:5432/otus",
    val user: String = "postgres",
    val password: String = "pass",
    val schema: String = "otus",
    // Delete tables at startup - needed for testing
    val dropDatabase: Boolean = false,
)
