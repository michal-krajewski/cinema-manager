package pl.byteit.cinemamanager.common

import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

data class DateWithZone (
    val dateTime: OffsetDateTime,
    val timeZone: ZoneId,
){

    companion object {
        fun from(zonedDateTime: ZonedDateTime): DateWithZone {
            return DateWithZone(
                zonedDateTime.toOffsetDateTime(),
                zonedDateTime.zone
            )
        }
    }

    fun toZonedDateTime(): ZonedDateTime = dateTime.atZoneSimilarLocal(timeZone)

}
