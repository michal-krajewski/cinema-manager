package pl.byteit.cinemamanager.movie.io


data class MovieScoreRequest(
    val score: Int
) {
    init {
        require(score in 1..5) { "Score must be between 1 and 5" }
    }
}
