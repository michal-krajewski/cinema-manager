package pl.byteit.cinemamanager.http

import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpMethod.*
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import java.util.UUID

interface HttpClient {

    fun<T> get(url: String, type: ParameterizedTypeReference<T>): T = exchange(url, GET, type, null).body!!

    fun put(url: String, body: Any) = exchange(url, PUT, object: ParameterizedTypeReference<Unit>(){}, body)

    fun delete(url: String) = exchange(url, DELETE, object: ParameterizedTypeReference<Unit>(){}, null)

    fun <T> exchange(
        endpoint: String,
        method: HttpMethod,
        parameterizedTypeReference: ParameterizedTypeReference<T>,
        body: Any?,
        headers: HttpHeaders? = null
    ): ResponseEntity<T>

}

class UserAwareHttpClient(private val userId: UUID, private val client: HttpClient) : HttpClient {
    override fun <T> exchange(
        endpoint: String,
        method: HttpMethod,
        parameterizedTypeReference: ParameterizedTypeReference<T>,
        body: Any?,
        headers: HttpHeaders?
    ): ResponseEntity<T> {
        val initializedHeaders = headers ?: HttpHeaders()
        initializedHeaders["User-Id"] = listOf(userId.toString())

        return client.exchange(endpoint, method, parameterizedTypeReference, body, initializedHeaders)
    }
}

class RestTemplateHttpClient(private val restTemplate: RestTemplate) : HttpClient {

    override fun <T> exchange(
        endpoint: String,
        method: HttpMethod,
        parameterizedTypeReference: ParameterizedTypeReference<T>,
        body: Any?,
        headers: HttpHeaders?
    ): ResponseEntity<T> {
        return restTemplate.exchange(
            endpoint,
            method,
            HttpEntity<Any>(body, headers),
            parameterizedTypeReference
        )
    }

}
