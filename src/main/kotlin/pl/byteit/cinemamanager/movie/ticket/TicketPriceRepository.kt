package pl.byteit.cinemamanager.movie.ticket

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface TicketPriceRepository: JpaRepository<TicketPrice, UUID> {
}
