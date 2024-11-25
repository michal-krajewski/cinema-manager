package pl.byteit.cinemamanager.security.login

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import pl.byteit.cinemamanager.util.MultiReadServletRequest

class LoginFilter(private val objectMapper: ObjectMapper) : UsernamePasswordAuthenticationFilter() {

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse?): Authentication {
        if (!request.contentType.startsWith("application/json")) {
            throw AuthenticationServiceException("Authentication content type not supported")
        }
        return super.attemptAuthentication(request, response)
    }

    override fun obtainPassword(request: HttpServletRequest): String {
        return readBodyField(request, "password")
    }

    override fun obtainUsername(request: HttpServletRequest): String {
        return readBodyField(request, "username")
    }

    private fun readBodyField(request: HttpServletRequest, fieldName: String): String {
        val body = MultiReadServletRequest.wrap(request).body()
        return objectMapper.readValue(body, ObjectNode::class.java)
            .get(fieldName)
            .textValue()
    }
}
