package data

/**
 * Repositorio simplificado que simula una base de datos usando una lista en memoria.
 * Los datos se perderán cuando cierres la aplicación.
 */
object UserRepository {

    // Lista mutable que simula la base de datos
    private val users = mutableListOf<User>()

    // Contador para generar IDs automáticamente
    private var nextId = 1

    /**
     * Inserta un nuevo usuario en la "base de datos"
     */
    fun insertUser(user: User) {
        val newUser = user.copy(id = nextId++)
        users.add(newUser)
    }

    /**
     * Busca un usuario por email
     */
    fun findUserByEmail(email: String): User? {
        return users.find { it.email == email }
    }

    /**
     * Obtiene todos los usuarios (útil para debug)
     */
    fun getAllUsers(): List<User> {
        return users.toList()
    }

    /**
     * Elimina todos los usuarios (útil para testing)
     */
    fun clearAll() {
        users.clear()
        nextId = 1
    }
}