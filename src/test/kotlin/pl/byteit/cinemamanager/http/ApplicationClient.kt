package pl.byteit.cinemamanager.http

import org.springframework.core.ParameterizedTypeReference
import pl.byteit.cinemamanager.movie.MovieDetailsResponse
import pl.byteit.cinemamanager.movie.MovieResponse
import pl.byteit.cinemamanager.movie.MovieScoreRequest
import pl.byteit.cinemamanager.movie.UserMovieScore
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
        httpClient.get("/users/$movieId/score", object : ParameterizedTypeReference<UserMovieScore>() {})
}
