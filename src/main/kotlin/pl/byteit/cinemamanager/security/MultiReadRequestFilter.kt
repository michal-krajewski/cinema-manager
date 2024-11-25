package pl.byteit.cinemamanager.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.ContentCachingRequestWrapper

class MultiReadRequestFilter: OncePerRequestFilter() {
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        filterChain.doFilter(ContentCachingRequestWrapper(request), response)
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean =
        !request.requestURL.toString().endsWith("/login")
}
