package data.network.dto

import data.Purchase

data class PurchaseRequest(
    val userId: String, // Email del usuario
    val userName: String,
    val shippingDetails: Any, // Puede ser un Map o un Objeto, el backend lo maneja como Object
    val items: List<PurchaseItemDto>,
    val totalAmount: Double
)

data class PurchaseItemDto(
    val id: Long,
    val title: String,
    val price: Double,
    val quantity: Int,
    val image: String
)
