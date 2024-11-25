package pl.byteit.cinemamanager.movie

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.tuple
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import pl.byteit.cinemamanager.IntegrationTestBase
import pl.byteit.cinemamanager.TestMovie.FAST_FIVE
import pl.byteit.cinemamanager.TestMovie.FAST_SIX
import pl.byteit.cinemamanager.http.RestPage

class MovieApiTest : IntegrationTestBase() {


    @Test
    fun `Should return movies`() {
        val response: List<MovieResponse> = client().getMovies().content

        assertThat(response)
            .hasSize(2)
            .extracting(MovieResponse::id, MovieResponse::title)
            .containsExactlyInAnyOrder(
                tuple(FAST_FIVE.id, FAST_FIVE.title),
                tuple(FAST_SIX.id, FAST_SIX.title),
            )
    }

    @Test
    @Disabled //TODO
    fun `Should return movie details from imdb`() {
        val movieDetails = client().getMovieDetails(FAST_FIVE.id)

        assertThat(movieDetails.details)
            .isEqualTo(DetailsDto(
                "143 min",
                "25 Jun 2021",
                "5.2",
                "Dom and the crew must take on an international terrorist who turns out to be Dom and Mia's estranged brother."
            ))
    }

    @Test
    fun `Should score movie`() {
        val client = client()

        client.addScore(FAST_FIVE.id, 2)

        val movieDetails = client.getMovieDetails(FAST_FIVE.id)
        assertThat(movieDetails.score).isEqualTo(2.0)
    }

    @Test
    fun `Should return average users score for movie`() {
        client().addScore(FAST_FIVE.id, 2)
        client().addScore(FAST_SIX.id, 3)

        val movieDetails = client().getMovieDetails(FAST_FIVE.id)
        assertThat(movieDetails.score).isEqualTo(2.5)
    }

    @Test
    @Disabled("Should be enabled when user support is added")
    fun `Should update user score`() {
        val client = client()
        client.addScore(FAST_FIVE.id, 2)
        val initialScore = client.getUserScore(FAST_FIVE.id)

        client.addScore(FAST_FIVE.id, 5)

        val movieDetails = client.getMovieDetails(FAST_FIVE.id)
        assertThat(movieDetails.score)
            .isNotEqualTo(initialScore.score)
            .isEqualTo(5)
    }

}
