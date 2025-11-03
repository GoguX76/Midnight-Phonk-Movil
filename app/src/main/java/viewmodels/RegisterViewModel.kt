package viewmodels

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import data.User
import data.UserRepository
import validations.AccountValidations

class RegisterViewModel : ViewModel() {
    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var confirmPassword by mutableStateOf("")
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
        updateConfirmPassword(confirmPassword)
    }

    fun updateConfirmPassword(newConfirmPassword: String) {
        confirmPassword = newConfirmPassword
        confirmPasswordError = if (password != newConfirmPassword) {
            "Las contraseñas no coinciden"
        } else {
            null
        }
    }

    fun isValid(): Boolean {
        return emailError == null && passwordError == null &&
                confirmPasswordError == null &&
                email.isNotEmpty() && password.isNotEmpty() &&
                confirmPassword.isNotEmpty()
    }

    suspend fun registerUser(): RegistrationResult {
        // Validar email vacío
        if (email.isBlank()) {
            return RegistrationResult.Error("El email no puede estar vacío")
        }

        // Validar contraseña vacía
        if (password.isBlank()) {
            return RegistrationResult.Error("La contraseña no puede estar vacía")
        }

        // Validar longitud de contraseña
        if (password.length < 6) {
            return RegistrationResult.Error("La contraseña debe tener al menos 6 caracteres")
        }

        // Validar que las contraseñas coincidan
        if (password != confirmPassword) {
            return RegistrationResult.Error("Las contraseñas no coinciden")
        }

        // Comprobamos si el usuario ya existe para evitar duplicados
        val existingUser = UserRepository.findUserByEmail(email)
        if (existingUser != null) {
            return RegistrationResult.Error("El email ya está en uso")
        }

        // En una app real, aquí se "hashea" la contraseña de forma segura
        val passwordHash = password.hashCode().toString()

        // Creamos el objeto User
        val newUser = User(email = email, passwordHash = passwordHash)

        // Usamos el repositorio para insertar el usuario en la "base de datos"
        UserRepository.insertUser(newUser)

        return RegistrationResult.Success(newUser)
    }
}

/**
 * Resultado de la operación de registro
 */
sealed class RegistrationResult {
    data class Success(val user: User) : RegistrationResult()
    data class Error(val message: String) : RegistrationResult()
}