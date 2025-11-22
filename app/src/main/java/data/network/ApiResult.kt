package data.network

import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import retrofit2.HttpException

// Estados de una petición a la API
sealed class ApiResult<out T> {
    object Loading : ApiResult<Nothing>()
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error(val message: String, val exception: Throwable? = null) : ApiResult<Nothing>()
}

// Envuelve llamadas a la API con manejo de errores automático
suspend fun <T> safeApiCall(apiCall: suspend () -> T): ApiResult<T> {
    return try {
        val response = apiCall()
        ApiResult.Success(response)
    } catch (e: Exception) {
        val errorMessage =
                when (e) {
                    is UnknownHostException -> "Sin conexión a internet"
                    is SocketTimeoutException -> "Tiempo de espera agotado"
                    is HttpException -> {
                        when (e.code()) {
                            400 -> "Solicitud incorrecta (400)"
                            401 -> "No autorizado (401)"
                            403 -> "Acceso denegado (403)"
                            404 -> "No encontrado (404)"
                            500 -> "Error del servidor (500)"
                            502 -> "Servidor no disponible (502)"
                            503 -> "Servicio no disponible (503)"
                            else -> "Error HTTP ${e.code()}"
                        }
                    }
                    is IOException -> "Error de red: ${e.message ?: "Problema de conexión"}"
                    else -> "Error: ${e.message ?: "Error desconocido"}"
                }
        ApiResult.Error(errorMessage, e)
    }
}
