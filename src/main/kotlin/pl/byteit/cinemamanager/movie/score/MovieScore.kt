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
    score: Int
) {
    final var score: Int = score
        private set

    fun update(score: Int): MovieScore {
        this.score = score
        return this
    }

}

@Embeddable
class MovieScoreId(var userId: UUID, var movieId: UUID) : Serializable
