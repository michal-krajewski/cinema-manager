package pl.byteit.cinemamanager

import java.util.*

enum class TestMovie(val id: UUID = UUID.randomUUID(), val title: String, val imdbId: String) {

    FAST_FIVE(title = "Fast Five", imdbId = "tt1596343"),
    FAST_SIX(title = "Fast & Furious 6", imdbId = "tt1905041"),

}
