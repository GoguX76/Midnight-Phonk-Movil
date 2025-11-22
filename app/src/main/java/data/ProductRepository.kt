package data

import data.network.ApiResult
import data.network.RetrofitClient
import data.network.safeApiCall

// Repositorio para manejar productos
object ProductRepository {

    // Obtiene todos los productos
    suspend fun getAllProducts(): ApiResult<List<Product>> {
        return safeApiCall { RetrofitClient.apiService.getAllProducts() }
    }

    // Busca un producto por ID
    suspend fun findProductById(id: Long): ApiResult<Product> {
        return safeApiCall { RetrofitClient.apiService.getProductById(id) }
    }

    // Crea un nuevo producto
    suspend fun insertProduct(product: Product): ApiResult<Product> {
        return safeApiCall { RetrofitClient.apiService.createProduct(product) }
    }

    // Actualiza un producto existente
    suspend fun updateProduct(product: Product): ApiResult<Product> {
        return safeApiCall { RetrofitClient.apiService.updateProduct(product.id.toLong(), product) }
    }

    // Elimina un producto por ID
    suspend fun deleteProduct(id: Long): ApiResult<Boolean> {
        return try {
            val response = RetrofitClient.apiService.deleteProduct(id)
            if (response.isSuccessful) {
                ApiResult.Success(true)
            } else {
                ApiResult.Error("Error al eliminar producto: ${response.code()}")
            }
        } catch (e: Exception) {
            ApiResult.Error("Error al eliminar producto: ${e.message}", e)
        }
    }
}
