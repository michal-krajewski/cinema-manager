package pl.byteit.cinemamanager.movie.ticket

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.util.*

@Entity
@Table(name = "movie_ticket_prices")
class TicketPrice (
    @Id val movieId: UUID,
    val price: BigDecimal
    ){
}
