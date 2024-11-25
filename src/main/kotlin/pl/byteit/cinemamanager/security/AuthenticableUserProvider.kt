package pl.byteit.cinemamanager.security

import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import pl.byteit.cinemamanager.user.UserService

class AuthenticableUserProvider(
    private val userService: UserService,
    private val passwordEncoder: PasswordEncoder
) {

    fun getByUsername(username: String): AuthenticableUser {
        val user = userService.findByUsername(username) ?: throw UsernameNotFoundException("User $username not found")
        return AuthenticableUser(user, passwordEncoder)
    }

}
