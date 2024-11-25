package pl.byteit.cinemamanager.movie.io

import java.math.BigDecimal


data class MovieScoreRequest(
    val score: Int
) {
    init {
        require(score in 1..5) { "Score must be between 1 and 5" }
    }
}

data class SetTicketPriceRequest(
    val ticketPrice: BigDecimal
) {
    init {
        require(ticketPrice > BigDecimal.ZERO) { "Ticket price must be positive" }
    }
}
