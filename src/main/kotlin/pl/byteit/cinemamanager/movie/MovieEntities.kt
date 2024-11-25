package pl.byteit.cinemamanager.movie

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.util.*

@Entity
@Table(name = "movies")
class Movie(
    @Id val id: UUID,
    val title: String,
    val imdbId: String
)

interface MovieDetailsWithScore {
    fun getId(): UUID
    fun getTitle(): String
    fun getImdbId(): String
    fun getTicketPrice(): BigDecimal?
    fun getScore(): Double?
}
