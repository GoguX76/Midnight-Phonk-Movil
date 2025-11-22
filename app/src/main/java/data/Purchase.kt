package data

// Modelo de datos para una compra
data class Purchase(
        val id: Long = 0,
        val userId: Long,
        val productId: Long,
        val quantity: Int,
        val totalPrice: Double = 0.0,
        val purchaseDate: String = "",
        val user: User? = null,
        val product: Product? = null
)
