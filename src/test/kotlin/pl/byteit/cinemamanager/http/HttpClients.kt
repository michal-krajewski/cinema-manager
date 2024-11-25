package pl.byteit.cinemamanager.http

import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpMethod.GET
import org.springframework.http.HttpMethod.PUT
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate

interface HttpClient {

    fun<T> get(url: String, type: ParameterizedTypeReference<T>): T = exchange(url, GET, type, null).body!!

    fun put(url: String, body: Any) = exchange(url, PUT, object: ParameterizedTypeReference<Unit>(){}, body)

    fun <T> exchange(
        endpoint: String,
        method: HttpMethod,
        parameterizedTypeReference: ParameterizedTypeReference<T>,
        body: Any?
    ): ResponseEntity<T>

}

class RestTemplateHttpClient(private val restTemplate: RestTemplate) : HttpClient {

    override fun <T> exchange(
        endpoint: String,
        method: HttpMethod,
        parameterizedTypeReference: ParameterizedTypeReference<T>,
        body: Any?
    ): ResponseEntity<T> {
        return restTemplate.exchange(
            endpoint,
            method,
            HttpEntity<Any>(body, null),
            parameterizedTypeReference
        )
    }

}
