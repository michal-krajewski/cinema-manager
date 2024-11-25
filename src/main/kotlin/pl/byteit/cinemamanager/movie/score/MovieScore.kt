package pl.byteit.cinemamanager.movie.score

import jakarta.persistence.*
import java.io.Serializable
import java.util.*

@Entity
@Table(name = "movie_scores")
@IdClass(MovieScoreId::class)
class MovieScore(
    @Id val userId: UUID,
    @Id val movieId: UUID,
    val score: Int
) {

    fun update(score: Int): MovieScore {
        return MovieScore(this.userId, this.movieId, score)
    }

}

@Embeddable
class MovieScoreId(var userId: UUID, var movieId: UUID) : Serializable
