package pl.byteit.cinemamanager.user

import jakarta.persistence.Entity
import jakarta.persistence.EnumType.STRING
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(name = "users")
class User(
    @Id val id: UUID,
    val username: String,
    val encodedPassword: String,
    @Enumerated(value = STRING) val role: Role
)

enum class Role {
    USER, ADMIN
}
