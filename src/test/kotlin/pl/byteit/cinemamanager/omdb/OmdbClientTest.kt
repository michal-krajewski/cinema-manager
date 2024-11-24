package pl.byteit.cinemamanager.omdb

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import pl.byteit.cinemamanager.common.CommonConfiguration

class OmdbClientTest {

    companion object {

        private val mockServer = OmdbMockServer("key")

        @BeforeAll
        @JvmStatic
        fun setup() {
            mockServer.start()
        }

        @AfterAll
        @JvmStatic
        fun tearDown() {
            mockServer.stop()
        }
    }

    private val omdbClientTest = OmdbClient(
        "http://localhost:${mockServer.port()}/",
        "key",
        CommonConfiguration().restTemplate()
    )

    @Test
    fun `Should map service response`() {
        mockServer.mockMovieResponse("movie-id")

        val fetchMovieDetails = omdbClientTest.fetchMovieDetails("movie-id")

        assertThat(fetchMovieDetails).isEqualTo(
            OmdbResponse(
                true,
                "F9: The Fast Saga",
                "143 min",
                "25 Jun 2021",
                "5.2",
                "Dom and the crew must take on an international terrorist who turns out to be Dom and Mia's estranged brother.",
                null
            )
        )
    }

    @Test
    fun `Should return null when received error response`() {
        mockServer.mockApiError("movie-id")

        val fetchMovieDetails = omdbClientTest.fetchMovieDetails("movie-id")

        assertThat(fetchMovieDetails).isNull()
    }

    @Test
    fun `Should return null when received server error`() {
        mockServer.mockServiceError("movie-id")

        val fetchMovieDetails = omdbClientTest.fetchMovieDetails("movie-id")

        assertThat(fetchMovieDetails).isNull()
    }

}
