package viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import validations.AccountValidations
import androidx.lifecycle.ViewModel

class RegisterViewModel: ViewModel() {
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

        if (confirmPassword.isNotEmpty()) {
            updateConfirmPassword(confirmPassword)
        }
    }

    fun updateConfirmPassword(newSecPassword: String) {
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