package viewmodels

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import data.User
import data.UserRepository
import data.network.ApiResult
import validations.AccountValidations

class LoginViewModel : ViewModel() {
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
        return emailError == null &&
                passwordError == null &&
                email.isNotEmpty() &&
                password.isNotEmpty()
    }

    suspend fun loginUser(): LoginResult {
        if (email.isBlank()) {
            return LoginResult.Error("El email no puede estar vacío")
        }

        if (password.isBlank()) {
            return LoginResult.Error("La contraseña no puede estar vacía")
        }

        // Buscar usuario por email usando API
        return when (val result = UserRepository.findUserByEmail(email)) {
            is ApiResult.Success -> {
                val user = result.data
                if (user == null) {
                    LoginResult.Error("Usuario no encontrado")
                } else {
                    // Verificar contraseña
                    if (user.password != password) {
                        LoginResult.Error("Contraseña incorrecta")
                    } else {
                        LoginResult.Success(user)
                    }
                }
            }
            is ApiResult.Error -> {
                LoginResult.Error(result.message)
            }
            is ApiResult.Loading -> {
                LoginResult.Error("Cargando...")
            }
        }
    }
}

// Resultado de la operación de login
sealed class LoginResult {
    data class Success(val user: User) : LoginResult()
    data class Error(val message: String) : LoginResult()
}
