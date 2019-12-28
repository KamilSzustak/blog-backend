package pl.kamilszustak.blog.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Primary
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import pl.kamilszustak.blog.repository.UserRepository

@Service
@Primary
class UserDetailsServiceImpl @Autowired constructor(
    private val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {
        val user = userRepository.findByEmail(username)
            ?: throw UsernameNotFoundException("Not found user with email: $username")


        println(user)

        val grantedAuthorities: MutableSet<GrantedAuthority> = mutableSetOf()

        user.roles.forEach {
            val authority = SimpleGrantedAuthority(it.name)
            grantedAuthorities.add(authority)
        }

        return User(user.email, user.password, grantedAuthorities)
    }
}