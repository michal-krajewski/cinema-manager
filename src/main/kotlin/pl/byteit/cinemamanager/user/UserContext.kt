package pl.byteit.cinemamanager.user

import org.springframework.web.context.request.RequestAttributes
import org.springframework.web.context.request.RequestContextHolder
import java.util.*


interface UserContext {
    fun currentUserId() : UUID {
        val id = RequestContextHolder.currentRequestAttributes().getAttribute("UserId", RequestAttributes.SCOPE_REQUEST) as String
        return UUID.fromString(id)
    }
}
