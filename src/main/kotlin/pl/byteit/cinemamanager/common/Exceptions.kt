package pl.byteit.cinemamanager.common

import java.util.*

open class NotFoundException(message: String) : RuntimeException(message)
class MovieNotFoundException(movieId: UUID) : NotFoundException("Movie $movieId not found")
class ScoreNotFound(movieId: UUID) : NotFoundException("User's score for $movieId not found")
