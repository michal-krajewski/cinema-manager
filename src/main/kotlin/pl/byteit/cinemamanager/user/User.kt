package pl.byteit.cinemamanager.user

import jakarta.persistence.*
import jakarta.persistence.EnumType.STRING
import java.util.*

@Entity
@Table(name = "users")
class User(
    @Id val id: UUID,
    @Enumerated(value = STRING) val role: Role
    ) {
}

enum class Role {
    USER, ADMIN
}
