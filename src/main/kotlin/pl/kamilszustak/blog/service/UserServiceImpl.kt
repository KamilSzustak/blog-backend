package pl.kamilszustak.blog.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Primary
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import pl.kamilszustak.blog.model.User
import pl.kamilszustak.blog.repository.RoleRepository
import pl.kamilszustak.blog.repository.UserRepository
import java.util.*
import kotlin.collections.HashSet


@Service
@Primary
class UserServiceImpl @Autowired constructor(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder
) : UserService {

    override fun save(user: User) {
        user.password = bCryptPasswordEncoder.encode(user.password)
        user.roles = HashSet(roleRepository.findAll())
        userRepository.save(user)
    }

    override fun findByUsername(username: String): User? = userRepository.findByEmail(username)

    override fun findAll(): List<User> = userRepository.findAll()
}