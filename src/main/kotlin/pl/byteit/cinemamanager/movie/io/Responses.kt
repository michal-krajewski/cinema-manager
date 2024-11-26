package pl.byteit.cinemamanager.movie.io

import com.fasterxml.jackson.annotation.JsonFormat
import pl.byteit.cinemamanager.movie.Movie
import pl.byteit.cinemamanager.movie.MovieDetails
import pl.byteit.cinemamanager.omdb.ImdbDetails
import java.math.BigDecimal
import java.util.*

data class MovieResponse(
    val id: UUID,
    val title: String
) {
    companion object Factory {
        fun from(movie: Movie): MovieResponse = MovieResponse(movie.id, movie.title)
    }
}

data class MovieDetailsResponse(
    val id: UUID,
    val title: String,
    val score: Double,
    @JsonFormat(shape = JsonFormat.Shape.STRING) val ticketPrice: BigDecimal?,
    val details: ImdbDetailsDto?
) {
    companion object Factory {
        fun from(movie: MovieDetails): MovieDetailsResponse = MovieDetailsResponse(
            movie.id(),
            movie.title(),
            movie.score() ?: 0.0,
            movie.ticketPrice(),
            ImdbDetailsDto.from(movie.imdbDetails)
        )
    }
}

data class ImdbDetailsDto(
    val runtime: String?,
    val releasedDate: String?,
    val imdbRating: String?,
    val description: String?
) {
    companion object Factory {
        fun from(imdb: ImdbDetails?): ImdbDetailsDto? {
            if (imdb == null)
                return null
            return ImdbDetailsDto(imdb.runtime, imdb.releasedDate, imdb.imdbRating, imdb.description)
        }
    }
}

data class UserMovieScore(val score: Int)
