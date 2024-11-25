package pl.byteit.cinemamanager.user

import org.springframework.security.core.context.SecurityContextHolder
import java.util.*


interface UserContext {
    fun currentUserId() : UUID {
        val authentication = SecurityContextHolder.getContext().authentication!!
        return authentication.details as UUID
    }
}
