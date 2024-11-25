package pl.byteit.cinemamanager.http

import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.*
import org.springframework.http.HttpMethod.*
import org.springframework.web.client.RestTemplate

interface HttpClient {

    fun<T> get(url: String, type: ParameterizedTypeReference<T>): T = exchange(url, GET, type, null).body!!

    fun put(url: String, body: Any) = exchange(url, PUT, object: ParameterizedTypeReference<Unit>(){}, body)

    fun delete(url: String) = exchange(url, DELETE, object: ParameterizedTypeReference<Unit>(){}, null)

    fun post(url: String, body: Any) {
        val httpHeaders = HttpHeaders()
        httpHeaders.contentType = MediaType.APPLICATION_JSON
        exchange(url, POST, object: ParameterizedTypeReference<Unit>(){}, body, httpHeaders)
    }

    fun <T> exchange(
        endpoint: String,
        method: HttpMethod,
        parameterizedTypeReference: ParameterizedTypeReference<T>,
        body: Any?,
        headers: HttpHeaders? = null
    ): ResponseEntity<T>

}

class StatefulHttpClient(
    private var savedCookie: HttpHeaders = HttpHeaders(),
    private val client: HttpClient
) : HttpClient {

    override fun <T> exchange(
        endpoint: String,
        method: HttpMethod,
        parameterizedTypeReference: ParameterizedTypeReference<T>,
        body: Any?,
        headers: HttpHeaders?
    ): ResponseEntity<T> {
        val initializedHeaders = headers ?: HttpHeaders()
        initializedHeaders.addAll(savedCookie)
        val response = client.exchange(endpoint, method, parameterizedTypeReference, body, initializedHeaders)

        response.headers["Set-Cookie"]
            ?.reduce { cookies, value -> "$cookies; $value" }
            ?.let { cookies -> savedCookie["Cookie"] = cookies }

        return response
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
