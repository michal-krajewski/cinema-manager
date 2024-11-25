package pl.byteit.cinemamanager.omdb

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class OmdbClientConfiguration {

    @Bean
    fun omdbClient(
        @Value("\${omdb-api.url}") omdbApiUrl: String,
        @Value("\${omdb-api.key}") apiKey: String,
        restTemplate: RestTemplate
    ): OmdbClient {
        return OmdbClient(omdbApiUrl, apiKey, restTemplate)
    }

}
