package viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import validations.AccountValidations

class RegisterViewModel {
    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var confirmPassword by mutableStateOf(value="")
        private set

    var emailError by mutableStateOf<String?>(null)
        private set

    var passwordError by mutableStateOf<String?>(null)
        private set

    var confirmPasswordError by mutableStateOf<String?>(null)
        private set

    fun updateEmail(newEmail: String) {
        email = newEmail
        emailError = AccountValidations.getEmailError(newEmail)
    }

    fun updatePassword(newPassword: String) {
        password = newPassword
        passwordError = AccountValidations.getPasswordError(newPassword)
    }

    private fun updateConfirmPassword(newConfirmPassword: String) {
        if (confirmPassword.isNotEmpty() && password != confirmPassword) {
            confirmPasswordError = "Las contraseñas no coinciden"
        } else {
            confirmPasswordError = null
        }
    }

    fun confirmPassword(newSecPassword: String) {
        confirmPassword = newSecPassword
        confirmPasswordError = AccountValidations.getConfirmPasswordError(password, newSecPassword)
    }

    fun isValid(): Boolean {
        return emailError == null &&
                passwordError == null &&
                confirmPasswordError == null &&
                email.isNotEmpty() &&
                password.isNotEmpty() &&
                confirmPassword.isNotEmpty()
    }
}