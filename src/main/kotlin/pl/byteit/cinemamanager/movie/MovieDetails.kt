package pl.byteit.cinemamanager.movie

import pl.byteit.cinemamanager.omdb.ImdbDetails

class MovieDetails(
    private val movieDetailsWithScore: MovieDetailsWithScore,
    val imdbDetails: ImdbDetails?
) {
    fun title() = movieDetailsWithScore.getTitle()
    fun score() = movieDetailsWithScore.getScore()
    fun id() = movieDetailsWithScore.getId()
    fun ticketPrice() = movieDetailsWithScore.getTicketPrice()
}
