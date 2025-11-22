package data.network

import data.Product
import data.Purchase
import data.User
import retrofit2.Response
import retrofit2.http.*

// Endpoints de la API REST del backend Spring Boot
interface ApiService {

    // ========== USUARIOS ==========

    @GET("api/users") suspend fun getAllUsers(): List<User>

    @GET("api/users/{id}") suspend fun getUserById(@Path("id") id: Long): User

    @POST("api/users") suspend fun createUser(@Body user: User): User

    @PUT("api/users/{id}") suspend fun updateUser(@Path("id") id: Long, @Body user: User): User

    @DELETE("api/users/{id}") suspend fun deleteUser(@Path("id") id: Long): Response<Unit>

    // ========== PRODUCTOS ==========

    @GET("api/products") suspend fun getAllProducts(): List<Product>

    @GET("api/products/{id}") suspend fun getProductById(@Path("id") id: Long): Product

    @POST("api/products") suspend fun createProduct(@Body product: Product): Product

    @PUT("api/products/{id}")
    suspend fun updateProduct(@Path("id") id: Long, @Body product: Product): Product

    @DELETE("api/products/{id}") suspend fun deleteProduct(@Path("id") id: Long): Response<Unit>

    // ========== COMPRAS ==========

    @GET("api/purchases") suspend fun getAllPurchases(): List<Purchase>

    @GET("api/purchases/{id}") suspend fun getPurchaseById(@Path("id") id: Long): Purchase

    // Obtiene compras de un usuario (historial)
    @GET("api/purchases/user/{userId}")
    suspend fun getPurchasesByUserId(@Path("userId") userId: Long): List<Purchase>

    // Obtiene compras de un producto (estadísticas)
    @GET("api/purchases/product/{productId}")
    suspend fun getPurchasesByProductId(@Path("productId") productId: Long): List<Purchase>

    // Crea una compra (usa @Query porque el backend usa @RequestParam)
    @POST("api/purchases")
    suspend fun createPurchase(
            @Query("userId") userId: Long,
            @Query("productId") productId: Long,
            @Query("quantity") quantity: Int
    ): Purchase

    @DELETE("api/purchases/{id}") suspend fun deletePurchase(@Path("id") id: Long): Response<Unit>
}
