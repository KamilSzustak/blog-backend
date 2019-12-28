package pl.kamilszustak.blog.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service


@Service
class SecurityServiceImpl @Autowired constructor(
    private val authenticationManager: AuthenticationManager,
    private val userDetailsService: UserDetailsService
) : SecurityService {

    override fun findLoggedInUsername(): String? {
        val userDetails = SecurityContextHolder.getContext().authentication.details
        return if (userDetails is UserDetails)
            userDetails.username
        else
            null
    }

    override fun autoLogin(username: String?, password: String?) {
        val userDetails = userDetailsService.loadUserByUsername(username)
        val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(userDetails, password, userDetails.authorities)

        authenticationManager.authenticate(usernamePasswordAuthenticationToken)

        if (usernamePasswordAuthenticationToken.isAuthenticated)
            SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
    }
}