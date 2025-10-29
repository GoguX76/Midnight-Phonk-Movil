package validations

object LoginValidations {
    fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidPassword(password: String): Boolean {
        return password.length >= 6
    }

    fun getEmailError(email: String): String? {
        return when {
            email.isEmpty() -> "Debe ingresar un correo para continuar"
            !isValidEmail(email) -> "Formato de correo inválido"
            else -> null
        }
    }

    fun getPasswordError(password: String): String? {
        return when {
            password.isEmpty() -> "Debe ingresar una contraseña para continuar"
            !isValidPassword(password) -> "La contraseña debe tener un mínimo de 6 caracteres"
            else -> null
        }
    }
}