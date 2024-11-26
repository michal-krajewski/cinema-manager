package pl.byteit.cinemamanager.user

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(val userService: UserService) {

    @PostMapping
    fun createUser(@RequestBody input: CreateUserRequest){
        userService.createUser(input.username, input.password)
    }

}

data class CreateUserRequest(val username: String, val password: String)
