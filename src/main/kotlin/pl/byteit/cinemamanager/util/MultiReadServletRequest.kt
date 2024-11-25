package pl.byteit.cinemamanager.util

import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.util.ContentCachingRequestWrapper

class MultiReadServletRequest(
    private val request: ContentCachingRequestWrapper,
    private var isInitialized: Boolean = false
) {

    companion object Factory {
        fun wrap(request: HttpServletRequest): MultiReadServletRequest {
            if (request !is ContentCachingRequestWrapper)
                throw IllegalArgumentException("Supports only ContentCachingRequestWrapper requests")

            return MultiReadServletRequest(request)
        }
    }

    fun body(): String {
        if (!isInitialized) {
            request.getBody()
            isInitialized = true
        }
        return String(request.contentAsByteArray)
    }

}
