package pl.byteit.cinemamanager

import pl.byteit.cinemamanager.user.Role
import java.util.*

enum class TestUser(val id: UUID = UUID.randomUUID(), val role: Role = Role.USER) {

    USER_1,
    USER_2,
    ADMIN(role = Role.ADMIN)

}
