package pl.byteit.cinemamanager.schedule

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface ShowScheduleRepository: JpaRepository<ShowSchedule, UUID> {

    fun findAllByMovieId(movieId: UUID, pageable: Pageable): Page<ShowSchedule>

}
