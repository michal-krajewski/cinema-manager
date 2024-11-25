package pl.byteit.cinemamanager.movie

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.*
import pl.byteit.cinemamanager.movie.io.MovieDetailsResponse
import pl.byteit.cinemamanager.movie.io.MovieResponse
import pl.byteit.cinemamanager.movie.io.MovieScoreRequest
import pl.byteit.cinemamanager.movie.io.UserMovieScore
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
