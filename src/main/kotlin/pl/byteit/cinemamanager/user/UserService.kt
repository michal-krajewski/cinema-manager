package pl.byteit.cinemamanager.user

import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class UserService(private val userRepository: UserRepository) {

    fun findByUsername(username: String): User? =
        userRepository.findByUsername(username).getOrNull()

}
