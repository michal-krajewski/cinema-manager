package pl.byteit.cinemamanager.movie

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface MovieRepository: JpaRepository<Movie, UUID> {

    @Query("""
        SELECT m.id AS id, m.title AS title, m.imdbId AS imdbId, t.price AS ticketPrice, AVG(ms.score) AS score
        FROM Movie m 
            LEFT JOIN TicketPrice t ON m.id = t.movieId
            LEFT JOIN MovieScore ms ON m.id = ms.movieId
        WHERE m.id = :id
        GROUP BY m.id, m.title, t.price 
    """)
    fun findWithScoreById(id: UUID): MovieDetailsWithScore?

}
