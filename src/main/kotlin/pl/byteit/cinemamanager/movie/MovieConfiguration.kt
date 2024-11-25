package pl.byteit.cinemamanager.movie

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.byteit.cinemamanager.movie.score.MovieScoreRepository
import pl.byteit.cinemamanager.omdb.OmdbClient
import pl.byteit.cinemamanager.user.UserContext

@Configuration
class MovieConfiguration {

    @Bean
    fun movieService(
        movieRepository: MovieRepository,
        movieScoreRepository: MovieScoreRepository,
        userContext: UserContext,
        omdbClient: OmdbClient
    ): MovieService {
        return MovieService(movieRepository, movieScoreRepository, userContext, omdbClient)
    }

}
