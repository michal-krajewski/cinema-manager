package pl.byteit.cinemamanager

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.core.env.Environment
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestExecutionListeners
import org.springframework.web.client.RestTemplate
import pl.byteit.cinemamanager.http.ApplicationClient
import pl.byteit.cinemamanager.http.RestTemplateErrorHandler
import pl.byteit.cinemamanager.http.RestTemplateHttpClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestExecutionListeners(
    value = [DatabaseCleaner::class, MovieDatabasePopulator::class],
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS
)
abstract class IntegrationTestBase {

    @Autowired
    private lateinit var environment: Environment

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    protected fun client(): ApplicationClient = ApplicationClient(RestTemplateHttpClient(httpClient()))

    protected fun httpClient(): RestTemplate {
        val restTemplate = RestTemplateBuilder()
            .errorHandler(RestTemplateErrorHandler())
            .rootUri("http://localhost:${environment.getProperty("local.server.port")}")
            .build()

        restTemplate.messageConverters
            .removeIf { it::class == MappingJackson2HttpMessageConverter::class }
        restTemplate.messageConverters
            .add(MappingJackson2HttpMessageConverter(objectMapper))

        return restTemplate
    }

}
