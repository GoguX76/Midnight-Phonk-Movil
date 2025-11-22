package data

import data.network.ApiResult
import data.network.RetrofitClient
import data.network.safeApiCall

// Repositorio para manejar usuarios
object UserRepository {

    // Obtiene todos los usuarios
    suspend fun getAllUsers(): ApiResult<List<User>> {
        return safeApiCall { RetrofitClient.apiService.getAllUsers() }
    }

    // Busca un usuario por ID
    suspend fun getUserById(id: Long): ApiResult<User> {
        return safeApiCall { RetrofitClient.apiService.getUserById(id) }
    }

    // Busca un usuario por email (obtiene todos y filtra)
    suspend fun findUserByEmail(email: String): ApiResult<User?> {
        return when (val result = getAllUsers()) {
            is ApiResult.Success -> {
                val user = result.data.find { it.email == email }
                ApiResult.Success(user)
            }
            is ApiResult.Error -> ApiResult.Error(result.message, result.exception)
            is ApiResult.Loading -> ApiResult.Loading
        }
    }

    // Crea un nuevo usuario
    suspend fun insertUser(user: User): ApiResult<User> {
        return safeApiCall { RetrofitClient.apiService.createUser(user) }
    }

    // Actualiza un usuario existente
    suspend fun updateUser(user: User): ApiResult<User> {
        return safeApiCall { RetrofitClient.apiService.updateUser(user.id.toLong(), user) }
    }

    // Elimina un usuario por ID
    suspend fun deleteUser(id: Long): ApiResult<Boolean> {
        return try {
            val response = RetrofitClient.apiService.deleteUser(id)
            if (response.isSuccessful) {
                ApiResult.Success(true)
            } else {
                ApiResult.Error("Error al eliminar usuario: ${response.code()}")
            }
        } catch (e: Exception) {
            ApiResult.Error("Error al eliminar usuario: ${e.message}", e)
        }
    }
}
