package pl.byteit.cinemamanager.movie

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/movies")
class MovieController(
    private val movieService: MovieService
) {

    @GetMapping
    fun getMovies(
        @RequestParam(required = false) page: Int = 0,
        @RequestParam(required = false) pageSize: Int = 50,
    ): Page<MovieResponse> {
        return movieService.getMovies(PageRequest.of(page, pageSize))
            .map(MovieResponse::from)
    }

    @GetMapping("/{movieId}")
    fun getMovie(@PathVariable movieId: UUID): MovieDetailsResponse {
        val movie = movieService.getMovieDetails(movieId)
        return MovieDetailsResponse.from(movie)
    }

    @GetMapping("/{movieId}/score")
    fun getUserScore(@PathVariable movieId: UUID): UserMovieScore {
        val score = movieService.getUserScore(movieId)
        return UserMovieScore(score)
    }

    @PutMapping("/{movieId}/score")
    fun scoreMovie(@PathVariable movieId: UUID, @RequestBody scoreRequest: MovieScoreRequest) {
        movieService.score(movieId, scoreRequest.score)
    }

}

data class MovieResponse(
    val id: UUID,
    val title: String
) {
    companion object Factory {
        fun from(movie: Movie): MovieResponse = MovieResponse(movie.id, movie.title)
    }
}

data class MovieDetailsResponse(
    val id: UUID,
    val title: String,
    val score: Double
) {
    companion object Factory {
        fun from(movie: MovieWithScore): MovieDetailsResponse = MovieDetailsResponse(movie.getId(), movie.getTitle(), movie.getScore())
    }
}

data class MovieScoreRequest(
    val score: Int
) {
    init {
        require(score in 1..5) { "Score must be between 1 and 5" }
    }
}

data class UserMovieScore(val score: Int)
