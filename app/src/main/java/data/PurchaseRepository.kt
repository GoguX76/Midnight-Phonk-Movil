package data

import data.network.ApiResult
import data.network.RetrofitClient
import data.network.safeApiCall

// Repositorio para manejar compras
object PurchaseRepository {

    // Obtiene todas las compras
    suspend fun getAllPurchases(): ApiResult<List<Purchase>> {
        return safeApiCall { RetrofitClient.apiService.getAllPurchases() }
    }

    // Obtiene una compra por ID
    suspend fun getPurchaseById(id: Long): ApiResult<Purchase> {
        return safeApiCall { RetrofitClient.apiService.getPurchaseById(id) }
    }

    // Obtiene compras de un usuario (historial)
    suspend fun getPurchasesByUserId(userId: Long): ApiResult<List<Purchase>> {
        return safeApiCall { RetrofitClient.apiService.getPurchasesByUserId(userId) }
    }

    // Obtiene compras de un producto (estadísticas)
    suspend fun getPurchasesByProductId(productId: Long): ApiResult<List<Purchase>> {
        return safeApiCall { RetrofitClient.apiService.getPurchasesByProductId(productId) }
    }

    // Crea una nueva compra
    suspend fun createPurchase(userId: Long, productId: Long, quantity: Int): ApiResult<Purchase> {
        return safeApiCall { RetrofitClient.apiService.createPurchase(userId, productId, quantity) }
    }

    // Elimina una compra
    suspend fun deletePurchase(id: Long): ApiResult<Boolean> {
        return try {
            val response = RetrofitClient.apiService.deletePurchase(id)
            if (response.isSuccessful) {
                ApiResult.Success(true)
            } else {
                ApiResult.Error("Error al eliminar compra: ${response.code()}")
            }
        } catch (e: Exception) {
            ApiResult.Error("Error al eliminar compra: ${e.message}", e)
        }
    }
}
