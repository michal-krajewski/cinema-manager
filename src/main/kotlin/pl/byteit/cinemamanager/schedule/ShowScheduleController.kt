package pl.byteit.cinemamanager.schedule

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.*
import pl.byteit.cinemamanager.common.DateWithZone
import java.util.*

@RestController
@RequestMapping("/shows")
class ShowScheduleController(
    private val showScheduleService: ShowScheduleService
) {

    @GetMapping
    fun getMovieShowsSchedule(
        @RequestParam(required = true) movieId: UUID,
        @RequestParam(required = false) page: Int = 0,
        @RequestParam(required = false) pageSize: Int = 50,
    ): Page<ShowResponse> =
        showScheduleService.getMovieShowSchedules(movieId, PageRequest.of(page, pageSize))
            .map { ShowResponse(it.id, it.movieId, DateWithZone.from(it.startTime)) }

    @PutMapping
    fun addSchedule(@RequestBody input: NewShowRequest) {
        showScheduleService.addMovieShowSchedule(input.movieId, input.startTime.toZonedDateTime())
    }

    @DeleteMapping("/{showId}")
    fun removeShowSchedule(@PathVariable showId: UUID) {
        showScheduleService.removeShowSchedule(showId)
    }

}

data class ShowResponse(
    val id: UUID,
    val movieId: UUID,
    val showDate: DateWithZone
)

data class NewShowRequest(
    val movieId: UUID,
    val startTime: DateWithZone,
)
