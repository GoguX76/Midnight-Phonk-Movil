package viewmodels

import androidx.compose.runtime.*
import validations.AccountValidations
import androidx.lifecycle.ViewModel
import data.User
import data.UserRepository

class LoginViewModel: ViewModel() {
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
        emailError = AccountValidations.getEmailError(newEmail)
    }

    fun updatePassword(newPassword: String) {
        password = newPassword
        passwordError = AccountValidations.getPasswordError(newPassword)
    }

    fun isValid(): Boolean {
        return emailError == null && passwordError == null &&
                email.isNotEmpty() && password.isNotEmpty()
    }

    suspend fun loginUser(): LoginResult {
        // Validar email vacío
        if (email.isBlank()) {
            return LoginResult.Error("El email no puede estar vacío")
        }

        // Validar contraseña vacía
        if (password.isBlank()) {
            return LoginResult.Error("La contraseña no puede estar vacía")
        }

        // Buscar el usuario por email
        val user = UserRepository.findUserByEmail(email)
        if (user == null) {
            return LoginResult.Error("Usuario no encontrado")
        }

        // Verificar la contraseña
        val passwordHash = password.hashCode().toString()
        if (user.passwordHash != passwordHash) {
            return LoginResult.Error("Contraseña incorrecta")
        }

        return LoginResult.Success(user)
    }
}

/**
 * Resultado de la operación de login
 */
sealed class LoginResult {
    data class Success(val user: User) : LoginResult()
    data class Error(val message: String) : LoginResult()
}