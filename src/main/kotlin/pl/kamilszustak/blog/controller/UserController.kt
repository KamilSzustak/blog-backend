package pl.kamilszustak.blog.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pl.kamilszustak.blog.auth.UserValidator
import pl.kamilszustak.blog.model.User
import pl.kamilszustak.blog.service.SecurityService
import pl.kamilszustak.blog.service.UserService

@RestController
@RequestMapping("/users")
@CrossOrigin
class UserController @Autowired constructor(
    private val userService: UserService,
    private val securityService: SecurityService,
    private val userValidator: UserValidator
) {

    @GetMapping("/login")
    fun loginUser(): ResponseEntity<Unit> = ResponseEntity(HttpStatus.OK)

    @PostMapping("/registration")
    fun registerUser(@RequestBody user: User): ResponseEntity<Unit> {
        userService.save(user)

        return ResponseEntity(HttpStatus.CREATED)
    }

    @GetMapping
    fun getAllUsers(): ResponseEntity<List<User>> {
        val users = userService.findAll()

        return ResponseEntity(users, HttpStatus.OK)
    }
}