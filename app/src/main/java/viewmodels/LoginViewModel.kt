package viewmodels

import androidx.compose.runtime.*
import validations.LoginValidations

class LoginViewModel {
    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var emailError by mutableStateOf<String?>(null)
        private set

    var passwordError by mutableStateOf<String?>(null)
        private set

    fun updateEmail(newEmail: String) {
        email = newEmail
        emailError = LoginValidations.getEmailError(newEmail)
    }

    fun updatePassword(newPassword: String) {
        password = newPassword
        passwordError = LoginValidations.getPasswordError(newPassword)
    }

    fun isValid(): Boolean {
        return emailError == null && passwordError == null &&
                email.isNotEmpty() && password.isNotEmpty()
    }
}