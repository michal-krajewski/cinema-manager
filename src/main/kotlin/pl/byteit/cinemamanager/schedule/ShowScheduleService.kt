package pl.byteit.cinemamanager.schedule

import org.springframework.data.domain.Pageable
import pl.byteit.cinemamanager.common.MovieNotFoundException
import pl.byteit.cinemamanager.movie.MovieService
import java.time.ZonedDateTime
import java.util.*

class ShowScheduleService(
    private val scheduleRepository: ShowScheduleRepository,
    private val movieService: MovieService
) {

    fun getMovieShowSchedules(movieId: UUID, pageable: Pageable) =
        scheduleRepository.findAllByMovieId(movieId, pageable)

    fun addMovieShowSchedule(movieId: UUID, startTime: ZonedDateTime) {
        if (!movieService.exists(movieId))
            throw MovieNotFoundException(movieId)

        scheduleRepository.save(ShowSchedule(movieId = movieId, startTime = startTime))
    }

    fun removeShowSchedule(scheduleId: UUID) =
        scheduleRepository.deleteById(scheduleId)

}
