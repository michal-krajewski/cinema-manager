package pl.byteit.cinemamanager.http

import org.springframework.core.ParameterizedTypeReference
import pl.byteit.cinemamanager.movie.io.MovieDetailsResponse
import pl.byteit.cinemamanager.movie.io.MovieResponse
import pl.byteit.cinemamanager.movie.io.MovieScoreRequest
import pl.byteit.cinemamanager.movie.io.UserMovieScore
import java.util.*

class ApplicationClient(private val httpClient: HttpClient) {

    fun getMovieDetails(movieId: UUID): MovieDetailsResponse =
        httpClient.get("/movies/$movieId", object : ParameterizedTypeReference<MovieDetailsResponse>() {})

    fun getMovies(): RestPage<MovieResponse> =
        httpClient.get("/movies", object : ParameterizedTypeReference<RestPage<MovieResponse>>() {})

    fun addScore(movieId: UUID, score: Int) {
        httpClient.put("/movies/$movieId/score", MovieScoreRequest(score))
    }

    fun getUserScore(movieId: UUID): UserMovieScore =
        httpClient.get("/movies/$movieId/score", object : ParameterizedTypeReference<UserMovieScore>() {})
}
