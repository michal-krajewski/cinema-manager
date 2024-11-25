package pl.byteit.cinemamanager

import org.springframework.context.ApplicationContext
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.TestContext
import org.springframework.test.context.TestExecutionListener
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.TransactionTemplate
import pl.byteit.cinemamanager.movie.Movie
import pl.byteit.cinemamanager.movie.MovieRepository
import pl.byteit.cinemamanager.user.User
import pl.byteit.cinemamanager.user.UserRepository
import java.util.*
import javax.sql.DataSource

class DatabaseCleaner: TestExecutionListener {

    override fun afterTestMethod(testContext: TestContext) {
        val applicationContext = testContext.applicationContext
        val jdbcTemplate = jdbcTemplate(applicationContext)

        transactionTemplate(applicationContext).execute{
            jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY FALSE")

            jdbcTemplate.queryForList("SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA='PUBLIC'").stream()
                .map { it.values.iterator().next() as String }
                .forEach { tableName: String -> jdbcTemplate.execute("TRUNCATE TABLE $tableName") }

            jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY TRUE")
        }
    }

    private fun jdbcTemplate(applicationContext: ApplicationContext): JdbcTemplate {
        val dataSource = applicationContext.getBean(DataSource::class.java)
        return JdbcTemplate(dataSource)
    }

    private fun transactionTemplate(applicationContext: ApplicationContext): TransactionTemplate {
        val txMgr = applicationContext.getBean(PlatformTransactionManager::class.java)
        return TransactionTemplate(txMgr)
    }
}


class MovieDatabasePopulator: TestExecutionListener {

    override fun beforeTestMethod(testContext: TestContext) {
        val applicationContext = testContext.applicationContext
        val movieRepository = applicationContext.getBean(MovieRepository::class.java)
        TestMovie.entries.forEach {
            movieRepository.save(Movie(it.id, it.title, it.imdbId))
        }
    }

}

class UserDatabasePopulator: TestExecutionListener {
    override fun beforeTestMethod(testContext: TestContext) {
        val applicationContext = testContext.applicationContext
        val passwordEncoder = applicationContext.getBean(PasswordEncoder::class.java)
        val userRepository = applicationContext.getBean(UserRepository::class.java)
        TestUser.entries.forEach {
            userRepository.save(User(
                UUID.randomUUID(),
                it.name,passwordEncoder.encode(it.password),
                it.role
            ))
        }
    }
}
