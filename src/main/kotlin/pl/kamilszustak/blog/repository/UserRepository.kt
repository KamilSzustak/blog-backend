package pl.kamilszustak.blog.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import pl.kamilszustak.blog.model.User

@Repository
interface UserRepository : JpaRepository<User, Long> {

    fun findByEmail(email: String?): User?
}