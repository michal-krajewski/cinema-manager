package pl.byteit.cinemamanager.movie

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import pl.byteit.cinemamanager.common.MovieNotFoundException
import pl.byteit.cinemamanager.common.ScoreNotFound
import pl.byteit.cinemamanager.movie.score.MovieScore
import pl.byteit.cinemamanager.movie.score.MovieScoreId
import pl.byteit.cinemamanager.movie.score.MovieScoreRepository
import pl.byteit.cinemamanager.omdb.ImdbDetails
import pl.byteit.cinemamanager.omdb.OmdbClient
import pl.byteit.cinemamanager.user.UserContext
import java.util.UUID

class MovieService(
    private val movieRepository: MovieRepository,
    private val movieScoreRepository: MovieScoreRepository,
    private val userContext: UserContext,
    private val omdbClient: OmdbClient
    ) {

    fun getMovies(pageRequest: PageRequest): Page<Movie> =
        movieRepository.findAll(pageRequest)

    fun getMovieDetails(movieId: UUID): MovieDetails {
        val movieWithScore = movieRepository.findWithScoreById(movieId) ?: throw MovieNotFoundException(movieId)
        val imdbDetails = omdbClient.fetchMovieDetails(movieWithScore.getImdbId())
        return MovieDetails(movieWithScore, imdbDetails)
    }

    fun getUserScore(movieId: UUID): Int {
        return movieScoreRepository.findById(MovieScoreId(userContext.currentUserId(), movieId))
            .map(MovieScore::score)
            .orElseThrow { ScoreNotFound(movieId) }
    }

    fun score(movieId: UUID, score: Int) {
        if(!movieRepository.existsById(movieId))
            throw MovieNotFoundException(movieId)

        val userId = userContext.currentUserId()
        val movieScore = movieScoreRepository.findById(MovieScoreId(userId, movieId))
            .map { it.update(score) }
            .orElseGet { MovieScore(userId, movieId, score) }
        movieScoreRepository.save(movieScore)
    }

    fun exists(movieId: UUID): Boolean =
        movieRepository.existsById(movieId)

}
