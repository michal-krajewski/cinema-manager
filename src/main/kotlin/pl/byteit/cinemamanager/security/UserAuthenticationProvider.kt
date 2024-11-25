package pl.byteit.cinemamanager.security

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication

class UserAuthenticationProvider(private val provider: AuthenticableUserProvider): AuthenticationProvider {
    override fun authenticate(authentication: Authentication): AuthenticatedUser =
        provider.getByUsername(authentication.name).authenticate(authentication)


    override fun supports(authentication: Class<*>?): Boolean =
        authentication == UsernamePasswordAuthenticationToken::class.java

}
