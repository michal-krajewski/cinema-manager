package pl.byteit.cinemamanager.schedule

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.ZonedDateTime
import java.util.*

@Entity
@Table(name = "show_schedules")
class ShowSchedule(
    @Id val id: UUID = UUID.randomUUID(),
    val movieId: UUID,
    val startTime: ZonedDateTime
    ) {
}
