package pl.byteit.cinemamanager.user

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun findByUsername(username: String): User? =
        userRepository.findByUsername(username).getOrNull()

    fun createUser(username: String, password: String) =
        userRepository.save(User(username = username, encodedPassword = passwordEncoder.encode(password)))

}
