package data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * El Repositorio maneja el almacenamiento de usuarios.
 * Ayuda a mantener el código organizado.
 */
object UserRepository {
    // Lista mutable privada para almacenar usuarios en memoria
    private val users = mutableListOf<User>()

    suspend fun findUserByEmail(email: String): User? = withContext(Dispatchers.IO) {
        users.find { it.email == email }
    }

    suspend fun insertUser(user: User) = withContext(Dispatchers.IO) {
        users.add(user)
    }
}
