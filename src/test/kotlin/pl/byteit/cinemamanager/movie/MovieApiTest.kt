package pl.byteit.cinemamanager.movie

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.tuple
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.web.client.getForObject
import pl.byteit.cinemamanager.IntegrationTestBase
import pl.byteit.cinemamanager.TestMovie.FAST_FIVE
import pl.byteit.cinemamanager.TestMovie.FAST_SIX
import pl.byteit.cinemamanager.http.RestPage

class MovieApiTest : IntegrationTestBase() {

    @Test
    fun `Should return movies`() {
        val response: List<MovieResponse> = client().exchange(
            "/movies",
            HttpMethod.GET,
            null,
            object : ParameterizedTypeReference<RestPage<MovieResponse>>() {}
        ).body!!.content

        assertThat(response)
            .hasSize(2)
            .extracting(MovieResponse::id, MovieResponse::title)
            .containsExactlyInAnyOrder(
                tuple(FAST_FIVE.id, FAST_FIVE.title),
                tuple(FAST_SIX.id, FAST_SIX.title),
            )
    }

    @Test
    fun `Should score movie`() {
        val httpClient = client()

        httpClient.exchange(
            "/movies/${FAST_FIVE.id}/score",
            HttpMethod.PUT,
            HttpEntity<MovieScoreRequest>(MovieScoreRequest(2)),
            Nothing::class.java
        )

        val movieDetails = httpClient.getForObject("/movies/${FAST_FIVE.id}", MovieDetailsResponse::class.java)!!
        assertThat(movieDetails.score).isEqualTo(2.0)
    }

    @Test
    fun `Should return average users score for movie`() {
        val httpClient = client()

        httpClient.exchange(
            "/movies/${FAST_FIVE.id}/score",
            HttpMethod.PUT,
            HttpEntity<MovieScoreRequest>(MovieScoreRequest(2)),
            Nothing::class.java
        )
        httpClient.exchange( //TODO: as another user
            "/movies/${FAST_FIVE.id}/score",
            HttpMethod.PUT,
            HttpEntity<MovieScoreRequest>(MovieScoreRequest(3)),
            Nothing::class.java
        )

        val movieDetails = httpClient.getForObject("/movies/${FAST_FIVE.id}", MovieDetailsResponse::class.java)!!
        assertThat(movieDetails.score).isEqualTo(2.5)
    }

    @Test
    @Disabled("Should be enabled when user support is added")
    fun `Should update user score`() {
        val httpClient = client()
        httpClient.exchange(
            "/movies/${FAST_FIVE.id}/score",
            HttpMethod.PUT,
            HttpEntity<MovieScoreRequest>(MovieScoreRequest(2)),
            Nothing::class.java
        )
        val initialScore = httpClient.getForObject("/movies/${FAST_FIVE.id}/score", UserMovieScore::class.java)!!

        httpClient.exchange(
            "/movies/${FAST_FIVE.id}/score",
            HttpMethod.PUT,
            HttpEntity<MovieScoreRequest>(MovieScoreRequest(5)),
            Nothing::class.java
        )

        val movieDetails = httpClient.getForObject("/movies/${FAST_FIVE.id}/score", UserMovieScore::class.java)!!
        assertThat(movieDetails.score)
            .isNotEqualTo(initialScore.score)
            .isEqualTo(5)
    }

}
