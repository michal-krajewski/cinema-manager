package pl.byteit.cinemamanager.movie.score

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MovieScoreRepository: JpaRepository<MovieScore, MovieScoreId> {
}
