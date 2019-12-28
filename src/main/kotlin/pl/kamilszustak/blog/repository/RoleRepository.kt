package pl.kamilszustak.blog.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import pl.kamilszustak.blog.model.Role

@Repository
interface RoleRepository : JpaRepository<Role, Long>