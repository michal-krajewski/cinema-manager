package pl.byteit.cinemamanager.user

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.servlet.HandlerInterceptor

class UserInterceptor: HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val id: String? = request.getHeader("User-Id")
        if (id != null)
            request.setAttribute("UserId", id)
        return true
    }

}
