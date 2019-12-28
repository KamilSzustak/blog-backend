package pl.kamilszustak.blog.service

interface SecurityService {

    fun findLoggedInUsername(): String?

    fun autoLogin(username: String?, password: String?)
}