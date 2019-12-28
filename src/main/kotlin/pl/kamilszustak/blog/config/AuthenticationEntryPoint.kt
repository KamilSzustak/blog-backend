package pl.kamilszustak.blog.config

import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class AuthenticationEntryPoint : BasicAuthenticationEntryPoint() {

    override fun commence(request: HttpServletRequest?, response: HttpServletResponse?, authException: AuthenticationException?) {
        if (response == null)
            return

        response.addHeader("WWW-Authenticate", "Basic realm=$realmName")
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        val writer = response.writer

        if (authException != null)
            writer.println("HTTP Status 401 - " + authException.message)
    }

    @Throws(Exception::class)
    override fun afterPropertiesSet() { // RealmName appears in the login window (Firefox).
        realmName = "Realm"
        super.afterPropertiesSet()
    }
}