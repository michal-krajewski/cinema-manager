package pl.byteit.cinemamanager.schedule

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import pl.byteit.cinemamanager.IntegrationTestBase
import pl.byteit.cinemamanager.TestMovie.FAST_FIVE
import pl.byteit.cinemamanager.TestUser.ADMIN
import pl.byteit.cinemamanager.TestUser.USER_1
import pl.byteit.cinemamanager.common.DateWithZone
import pl.byteit.cinemamanager.http.NotFoundResponseException
import pl.byteit.cinemamanager.http.ForbiddenResponseException
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

class ShowScheduleApiTest: IntegrationTestBase() {

    private val baseDate = ZonedDateTime.of(
        LocalDateTime.of(2020, 11, 10, 11, 11, 0),
        ZoneId.of("+00:00")
    )

    @Test
    fun `Should create schedules for movie`() {
        val asAdmin = login(ADMIN)

        asAdmin.scheduleMovieShow(FAST_FIVE.id, baseDate)
        asAdmin.scheduleMovieShow(FAST_FIVE.id, baseDate.plusHours(1))

        val asUser = login(USER_1)
        assertThat(asUser.getMovieShows(FAST_FIVE.id).content)
            .extracting("movieId", "showDate")
            .containsExactlyInAnyOrder(
                tuple(FAST_FIVE.id, DateWithZone.from(baseDate)),
                tuple(FAST_FIVE.id, DateWithZone.from(baseDate.plusHours(1)))
            )
    }

    @Test
    fun `Should return empty list for movie without schedule`() {
        val movieShows = login(USER_1).getMovieShows(FAST_FIVE.id)

        assertThat(movieShows).isEmpty()
    }

    @Test
    fun `Should remove show`() {
        val asAdmin = login(ADMIN)
        asAdmin.scheduleMovieShow(FAST_FIVE.id, baseDate)
        asAdmin.scheduleMovieShow(FAST_FIVE.id, baseDate.plusHours(1))
        val showId = getShowId(FAST_FIVE.id, baseDate)

        asAdmin.deleteScheduledShow(showId)

        assertThat(asAdmin.getMovieShows(FAST_FIVE.id))
            .singleElement()
            .extracting("id")
            .isEqualTo(getShowId(FAST_FIVE.id, baseDate.plusHours(1)))
    }

    @Test
    fun `Should throw exception when scheduled show movie does not exist`() {
        val asAdmin = login(ADMIN)

        assertThatThrownBy { asAdmin.scheduleMovieShow(UUID.randomUUID(), baseDate) }
            .isExactlyInstanceOf(NotFoundResponseException::class.java)
    }

    @Test
    @Disabled("Security settings needed")
    fun `User cannot change movie show`() {
        val asUser = login(USER_1)

        assertThatThrownBy { asUser.deleteScheduledShow(UUID.randomUUID()) }
            .isExactlyInstanceOf(ForbiddenResponseException::class.java)
        assertThatThrownBy { asUser.scheduleMovieShow(UUID.randomUUID(), baseDate) }
            .isExactlyInstanceOf(ForbiddenResponseException::class.java)
    }

    private fun getShowId(movieId: UUID, startTime: ZonedDateTime): UUID {
        return login(USER_1).getMovieShows(movieId)
            .first { it.showDate == DateWithZone.from(startTime) }
            .id
    }

}
