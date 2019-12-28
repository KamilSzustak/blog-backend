package pl.kamilszustak.blog.auth

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.validation.Errors
import org.springframework.validation.ValidationUtils
import org.springframework.validation.Validator
import pl.kamilszustak.blog.model.User
import pl.kamilszustak.blog.service.UserService

@Component
class UserValidator @Autowired constructor(
    private val userService: UserService
) : Validator {

    override fun validate(userObject: Any, errors: Errors) {
        val user = userObject as User

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
        if (user.email.length < 6 || user.email.length > 32) {
            errors.rejectValue("username", "Size.userForm.username");
        }
        if (userService.findByUsername(user.email) != null) {
            errors.rejectValue("username", "Duplicate.userForm.username");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (user.password.length < 8 || user.password.length > 32) {
            errors.rejectValue("password", "Size.userForm.password");
        }
    }

    override fun supports(supportClass: Class<*>): Boolean {
        return User::class.java == supportClass
    }
}