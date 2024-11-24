package pl.byteit.cinemamanager.common

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.client.NoOpResponseErrorHandler
import org.springframework.web.client.RestTemplate

@Configuration
class CommonConfiguration {

    @Bean
    fun objectMapper(): ObjectMapper {
        return ObjectMapper()
            .registerKotlinModule()
            .registerModules(JavaTimeModule())
            .enable(DeserializationFeature.FAIL_ON_TRAILING_TOKENS, DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
    }

    @Bean
    fun restTemplate(): RestTemplate {
        val restTemplate = RestTemplateBuilder()
            .errorHandler(NoOpResponseErrorHandler())
            .build()

        restTemplate.messageConverters
            .removeIf { it::class == MappingJackson2HttpMessageConverter::class }
        restTemplate.messageConverters
            .add(MappingJackson2HttpMessageConverter(objectMapper()))

        return restTemplate
    }

}
