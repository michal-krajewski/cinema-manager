package pl.byteit.cinemamanager

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CinemaManagerApplication

fun main(args: Array<String>) {
    runApplication<CinemaManagerApplication>(*args)
}
