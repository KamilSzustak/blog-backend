package pl.kamilszustak.blog.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint


@Configuration
@EnableWebSecurity(debug = true)
class WebSecurityConfiguration @Autowired constructor(
    private val userDetailsService: UserDetailsService,
    private val authenticationEntryPoint: AuthenticationEntryPoint
) : WebSecurityConfigurerAdapter() {

    override fun configure(auth: AuthenticationManagerBuilder?) {
        if (auth == null)
            return

        auth
            .userDetailsService(userDetailsService)
            .passwordEncoder(getBCryptPasswordEncoder())
            .and()
            .inMemoryAuthentication()
            .withUser("user")
            .password("password")
            .roles("USER")
            .and()
            .withUser("admin")
            .password("password")
            .roles("ADMIN", "USER")
    }

    override fun configure(http: HttpSecurity?) {
        if (http == null)
            return

        http
            .cors()
            .and()
            .csrf().disable()
            .exceptionHandling()
            .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/users/**")
                .permitAll()
            .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
            .and()
                .httpBasic()
                .authenticationEntryPoint(authenticationEntryPoint)
    }

//    override fun configure(web: WebSecurity?) {
//        if (web == null)
//            return
//
//        web.ignoring()
//            .antMatchers("/api/user/registration")
//    }

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager =
        super.authenticationManagerBean()

    @Bean
    fun getBCryptPasswordEncoder(): BCryptPasswordEncoder =
        BCryptPasswordEncoder(12)
}