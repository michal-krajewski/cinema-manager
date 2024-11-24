package pl.byteit.cinemamanager.user

import java.util.*


interface UserContext {
    fun currentUserId() : UUID {
        return UUID.randomUUID() //TODO: return user id from user context
    }
}
