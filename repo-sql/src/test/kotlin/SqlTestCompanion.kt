package site.geniyz.otus.backend.repo.sql

import com.benasher44.uuid.uuid4
import org.testcontainers.containers.PostgreSQLContainer
import java.time.Duration

import site.geniyz.otus.common.models.*

class PostgresContainer : PostgreSQLContainer<PostgresContainer>("postgres:13.2")

object SqlTestCompanion {
    private const val USER   = "postgres"
    private const val PASS   = "pass"
    private const val SCHEMA = "otus"

    private val container by lazy {
        PostgresContainer().apply {
            withUsername(USER)
            withPassword(PASS)
            withDatabaseName(SCHEMA)
            withStartupTimeout(Duration.ofSeconds(300L))
            start()
        }
    }

    private val url: String by lazy { container.jdbcUrl }

    fun repoUnderTestContainer(

        initObjs:  List<AppObj> = emptyList(),
        initTags:  List<AppTag> = emptyList(),
        initLinks: Map<String, List<String>> = emptyMap(),

        randomUuid: () -> String = { uuid4().toString() },
    ): RepoSQL {
        return RepoSQL(SqlProperties(url, USER, PASS, SCHEMA, dropDatabase = true),
            initObjs, initTags, initLinks,
            randomUuid = randomUuid,
            )
    }
}
