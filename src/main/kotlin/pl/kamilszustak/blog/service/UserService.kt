package pl.kamilszustak.blog.service

import pl.kamilszustak.blog.model.User

interface UserService {

    fun save(user: User)

    fun findByUsername(username: String): User?

    fun findAll(): List<User>
}