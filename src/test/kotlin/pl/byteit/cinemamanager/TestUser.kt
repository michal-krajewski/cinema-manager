package pl.byteit.cinemamanager

import pl.byteit.cinemamanager.user.Role
import java.util.*

enum class TestUser(val id: UUID = UUID.randomUUID(), val password: String, val role: Role = Role.USER) {

    USER_1(password = "user-1-passwd"),
    USER_2(password = "user-2-passwd"),
    ADMIN(password = "admin-passwd", role = Role.ADMIN)

}
