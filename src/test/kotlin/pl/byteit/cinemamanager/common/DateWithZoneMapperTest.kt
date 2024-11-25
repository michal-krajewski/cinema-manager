package pl.byteit.cinemamanager.common

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.*

class DateWithZoneMapperTest {

    private val objectMapper = CommonConfiguration().objectMapper()

    @Test
    fun `Should map json to DateWithZone`() {
        val json = """
            {
                "dateTime": "2020-11-10T11:11:00+01:00",
                "timeZone": "Europe/Warsaw"
            }
        """.trimIndent()

        val dateWithZone = objectMapper.readValue(json, DateWithZone::class.java)

        assertThat(dateWithZone)
            .extracting("dateTime", "timeZone")
            .containsExactly(
                OffsetDateTime.of(LocalDateTime.of(2020, 11, 10, 11, 11, 0), ZoneOffset.ofHours(1)),
                ZoneId.of("Europe/Warsaw")
            )
    }

    @Test
    fun `Should map to ZonedDateTime`() {
        val dateWithZone = DateWithZone(
            OffsetDateTime.of(LocalDateTime.of(2020, 11, 10, 11, 11, 0), ZoneOffset.ofHours(1)),
            ZoneId.of("Europe/Warsaw")
        )

        val zonedDateTime = dateWithZone.toZonedDateTime()

        assertThat(zonedDateTime)
            .isEqualTo(
                ZonedDateTime.of(
                    LocalDateTime.of(2020, 11, 10, 11, 11, 0),
                    ZoneOffset.ofHours(1)
                )
            )
    }

    @Test
    fun `Should create from ZonedDateTime`() {
        val zonedDateTime = ZonedDateTime.of(
            LocalDateTime.of(2020, 11, 10, 11, 11, 0),
            ZoneId.of("Europe/Warsaw")
        )

        val dateWithZone = DateWithZone.from(zonedDateTime)

        assertThat(dateWithZone)
            .isEqualTo(DateWithZone(
                OffsetDateTime.of(LocalDateTime.of(2020, 11, 10, 11, 11, 0), ZoneOffset.ofHours(1)),
                ZoneId.of("Europe/Warsaw")
            ))
    }

}
