package pl.byteit.cinemamanager.movie

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(name = "movies")
class Movie(
    @Id var id: UUID,
    var title: String,
    var imdbId: String
) {
}

interface MovieWithScore {
    fun getId(): UUID
    fun getTitle(): String
    fun getScore(): Double
}

//@JvmRecord
//data class MovieWithScore(
//    val id: UUID,
//    val title: String,
//    val score: Double
//)
