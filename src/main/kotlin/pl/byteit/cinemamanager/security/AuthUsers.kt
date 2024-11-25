package pl.byteit.cinemamanager.security

import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import pl.byteit.cinemamanager.user.User
import java.util.*

class AuthenticatedUser(private val user: User) : Authentication {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
        mutableSetOf(SimpleGrantedAuthority("ROLE_${user.role}"))

    override fun getName(): String = user.username

    override fun getCredentials(): Any = user.encodedPassword

    override fun getDetails(): UUID = user.id

    override fun getPrincipal(): Any = user.username

    override fun isAuthenticated(): Boolean = true

    override fun setAuthenticated(isAuthenticated: Boolean) {
        throw IllegalStateException("cannot change authentication status")
    }
}

class AuthenticableUser(
    private val user: User,
    private val passwordEncoder: PasswordEncoder
) : UserDetails {

    fun authenticate(toBeAuthenticated: Authentication): AuthenticatedUser {
        if (!passwordEncoder.matches(toBeAuthenticated.credentials as String, user.encodedPassword) || toBeAuthenticated.name != getUsername())
            throw BadCredentialsException("Invalid username or password")
        return AuthenticatedUser(user)
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
        mutableSetOf(SimpleGrantedAuthority("ROLE_${user.role}"))

    override fun getPassword(): String =
        user.encodedPassword

    override fun getUsername(): String =
        user.username

    override fun isEnabled(): Boolean =
        false
}
