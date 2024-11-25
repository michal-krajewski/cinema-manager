package pl.byteit.cinemamanager.movie

import pl.byteit.cinemamanager.omdb.ImdbDetails

class MovieDetails(
    private val movieWithScore: MovieWithScore,
    val imdbDetails: ImdbDetails?
) {
    fun title() = movieWithScore.getTitle()
    fun score() = movieWithScore.getScore()
    fun id() = movieWithScore.getId()
}
