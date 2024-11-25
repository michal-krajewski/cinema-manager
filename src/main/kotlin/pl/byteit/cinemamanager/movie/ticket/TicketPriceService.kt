package pl.byteit.cinemamanager.movie.ticket

import org.springframework.transaction.annotation.Transactional
import pl.byteit.cinemamanager.common.MovieNotFoundException
import pl.byteit.cinemamanager.movie.MovieService
import java.math.BigDecimal
import java.util.*

open class TicketPriceService(
    private val repository: TicketPriceRepository,
    private val movieService: MovieService) {

    //TODO: admin only
    @Transactional
    open fun setTicketPrice(movieId: UUID, ticketPrice: BigDecimal) {
        if(!movieService.exists(movieId))
            throw MovieNotFoundException(movieId)

        repository.deleteById(movieId)
        repository.save(TicketPrice(movieId, ticketPrice))
    }

}
