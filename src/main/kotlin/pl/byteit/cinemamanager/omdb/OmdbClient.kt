package pl.byteit.cinemamanager.omdb

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import org.slf4j.LoggerFactory
import org.springframework.web.client.RestTemplate

class OmdbClient(
    private val rootUrl: String,
    private val apiKey: String,
    private val httpClient: RestTemplate
) {
    private val log = LoggerFactory.getLogger(OmdbClient::class.java)

    fun fetchMovieDetails(omdbId: String): ImdbDetails? {
        log.debug("Sending request to omdb API for movie: $omdbId")
        val response: OmdbResponse? = fetchRequest(omdbId)
        if (response == null || response.isError()) {
            log.error("Received error response from omdb: ${response?.error}")
            return null
        }
        return ImdbDetails(
            response.runtime,
            response.releaseDate,
            response.imdbRating,
            response.plot
        )
    }

    private fun fetchRequest(omdbId: String): OmdbResponse? {
        val responseEntity = httpClient.getForEntity("$rootUrl?apikey=$apiKey&i=$omdbId", OmdbResponse::class.java)
        if (responseEntity.statusCode.isError) {
            log.error("Received response with status code ${responseEntity.statusCode}")
            return null
        }
        return responseEntity.body
    }

}

data class ImdbDetails(
    val runtime: String?,
    val releasedDate: String?,
    val imdbRating: String?,
    val description: String?
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class OmdbResponse(
    @JsonProperty("Response") val isResponse: Boolean,
    @JsonProperty("Title") val title: String?,
    @JsonProperty("Runtime") val runtime: String?,
    @JsonProperty("Released") val releaseDate: String?,
    @JsonProperty("imdbRating") val imdbRating: String?,
    @JsonProperty("Plot") val plot: String?,
    @JsonProperty("Error") val error: String?
) {
    fun isError(): Boolean = !isResponse
}

