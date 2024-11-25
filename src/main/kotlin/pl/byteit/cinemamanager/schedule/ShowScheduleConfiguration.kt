package pl.byteit.cinemamanager.schedule

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.byteit.cinemamanager.movie.MovieService

@Configuration
class ShowScheduleConfiguration {

    @Bean
    fun showScheduleService(repository: ShowScheduleRepository, movieService: MovieService) =
        ShowScheduleService(repository, movieService)

}
