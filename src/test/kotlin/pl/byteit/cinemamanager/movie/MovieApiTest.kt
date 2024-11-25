package pl.byteit.cinemamanager.movie

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import pl.byteit.cinemamanager.IntegrationTestBase
import pl.byteit.cinemamanager.TestMovie.FAST_FIVE
import pl.byteit.cinemamanager.TestMovie.FAST_SIX
import pl.byteit.cinemamanager.TestUser
import pl.byteit.cinemamanager.TestUser.ADMIN
import pl.byteit.cinemamanager.http.ApplicationClient
import pl.byteit.cinemamanager.http.ForbiddenResponseException
import pl.byteit.cinemamanager.movie.io.ImdbDetailsDto
import pl.byteit.cinemamanager.movie.io.MovieResponse
import pl.byteit.cinemamanager.omdb.OmdbMockServer
import java.math.BigDecimal

class MovieApiTest : IntegrationTestBase() {

    companion object {

        private val mockServer = OmdbMockServer("secret-test-key", 8585)

        @BeforeAll
        @JvmStatic
        fun setupMockServer() {
            mockServer.start()
        }

        @AfterAll
        @JvmStatic
        fun tearDownMockServer() {
            mockServer.stop()
        }
    }

    lateinit var asUser: ApplicationClient

    @BeforeEach
    fun setupMovieApiTest() {
        mockServer.mockMovieResponse(FAST_FIVE.imdbId)
        mockServer.mockMovieResponse(FAST_SIX.imdbId)

        asUser = login(TestUser.USER_1)
    }

    @Test
    fun `Should return movies`() {
        val response: List<MovieResponse> = asUser.getMovies().content

        assertThat(response)
            .hasSize(2)
            .extracting(MovieResponse::id, MovieResponse::title)
            .containsExactlyInAnyOrder(
                tuple(FAST_FIVE.id, FAST_FIVE.title),
                tuple(FAST_SIX.id, FAST_SIX.title),
            )
    }

    @Test
    fun `Should return movie details from imdb`() {
        val movieDetails = asUser.getMovieDetails(FAST_FIVE.id)

        assertThat(movieDetails.details)
            .isEqualTo(
                ImdbDetailsDto(
                "143 min",
                "25 Jun 2021",
                "5.2",
                "Dom and the crew must take on an international terrorist who turns out to be Dom and Mia's estranged brother."
                )
            )
    }

    @Test
    fun `Should score movie`() {
        asUser.addScore(FAST_FIVE.id, 2)

        val movieDetails = asUser.getMovieDetails(FAST_FIVE.id)
        assertThat(movieDetails.score).isEqualTo(2.0)
    }

    @Test
    fun `Should return average users score for movie`() {
        asUser.addScore(FAST_FIVE.id, 2)
        login(TestUser.USER_2).addScore(FAST_FIVE.id, 3)

        val movieDetails = asUser.getMovieDetails(FAST_FIVE.id)

        assertThat(movieDetails.score).isEqualTo(2.5)
    }

    @Test
    fun `Should update user score`() {
        asUser.addScore(FAST_FIVE.id, 2)
        val initialScore = asUser.getUserScore(FAST_FIVE.id)

        asUser.addScore(FAST_FIVE.id, 5)

        val movieDetails = asUser.getMovieDetails(FAST_FIVE.id)
        assertThat(movieDetails.score)
            .isNotEqualTo(initialScore.score)
            .isEqualTo(5.0)
    }

    @Test
    fun `Should set ticket price`() {
        val asAdmin = login(ADMIN)

        asAdmin.setTicketPrice(FAST_FIVE.id, BigDecimal("10.99"))

        val movieDetails = asUser.getMovieDetails(FAST_FIVE.id)
        assertThat(movieDetails.ticketPrice)
            .isEqualTo(BigDecimal("10.99"))
    }

    @Test
    fun `Should change current ticket price`() {
        val asAdmin = login(ADMIN)
        asAdmin.setTicketPrice(FAST_FIVE.id, BigDecimal("10.99"))

        asAdmin.setTicketPrice(FAST_FIVE.id, BigDecimal("19.99"))

        val movieDetails = asUser.getMovieDetails(FAST_FIVE.id)
        assertThat(movieDetails.ticketPrice)
            .isEqualTo(BigDecimal("19.99"))
    }

    @Test
    @Disabled("Security config needed")
    fun `Should prevent user from setting ticket price`() {
        assertThatThrownBy { asUser.setTicketPrice(FAST_FIVE.id, BigDecimal("19.99")) }
            .isExactlyInstanceOf(ForbiddenResponseException::class.java)
    }

    @Test
    fun `Should return null value when price is not defined`() {
        val movieDetails = asUser.getMovieDetails(FAST_FIVE.id)

        assertThat(movieDetails.ticketPrice)
            .isNull()
    }

}
