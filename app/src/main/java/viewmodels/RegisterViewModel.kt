package viewmodels

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import data.User
import data.UserRepository
import data.network.ApiResult
import validations.AccountValidations

class RegisterViewModel : ViewModel() {
    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var confirmPassword by mutableStateOf("")
        private set

    var name by mutableStateOf("")
        private set

    var emailError by mutableStateOf<String?>(null)
        private set

    var passwordError by mutableStateOf<String?>(null)
        private set

    var confirmPasswordError by mutableStateOf<String?>(null)
        private set

    var nameError by mutableStateOf<String?>(null)
        private set

    fun updateEmail(newEmail: String) {
        email = newEmail
        emailError = AccountValidations.getEmailError(newEmail)
    }

    fun updatePassword(newPassword: String) {
        password = newPassword
        passwordError = AccountValidations.getPasswordError(newPassword)
        updateConfirmPassword(confirmPassword)
    }

    fun updateConfirmPassword(newConfirmPassword: String) {
        confirmPassword = newConfirmPassword
        confirmPasswordError =
                if (password != newConfirmPassword) {
                    "Las contraseñas no coinciden"
                } else {
                    null
                }
    }

    fun updateName(newName: String) {
        name = newName
        nameError = if (newName.isBlank()) {
            "El nombre no puede estar vacío"
        } else {
            null
        }
    }

    fun isValid(): Boolean {
        return emailError == null &&
                passwordError == null &&
                confirmPasswordError == null &&
                nameError == null &&
                email.isNotEmpty() &&
                password.isNotEmpty() &&
                confirmPassword.isNotEmpty() &&
                name.isNotEmpty()
    }

    suspend fun registerUser(): RegistrationResult {
        if (email.isBlank()) {
            return RegistrationResult.Error("El email no puede estar vacío")
        }

        if (password.isBlank()) {
            return RegistrationResult.Error("La contraseña no puede estar vacía")
        }

        if (password.length < 6) {
            return RegistrationResult.Error("La contraseña debe tener al menos 6 caracteres")
        }

        if (password != confirmPassword) {
            return RegistrationResult.Error("Las contraseñas no coinciden")
        }

        // Verificar si el usuario ya existe
        return when (val existingUserResult = UserRepository.findUserByEmail(email)) {
            is ApiResult.Success -> {
                if (existingUserResult.data != null) {
                    RegistrationResult.Error("El email ya está en uso")
                } else {
                    // Crear nuevo usuario
                    val newUser = User(email = email, password = password, name = name)

                    when (val insertResult = UserRepository.insertUser(newUser)) {
                        is ApiResult.Success -> {
                            RegistrationResult.Success(insertResult.data)
                        }
                        is ApiResult.Error -> {
                            RegistrationResult.Error(insertResult.message)
                        }
                        is ApiResult.Loading -> {
                            RegistrationResult.Error("Cargando...")
                        }
                    }
                }
            }
            is ApiResult.Error -> {
                RegistrationResult.Error(existingUserResult.message)
            }
            is ApiResult.Loading -> {
                RegistrationResult.Error("Cargando...")
            }
        }
    }
}

// Resultado de la operación de registro
sealed class RegistrationResult {
    data class Success(val user: User) : RegistrationResult()
    data class Error(val message: String) : RegistrationResult()
}
