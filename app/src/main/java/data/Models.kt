
package data

data class Product(
    val id: Int,
    val name: String,
    val price: Double // Changed from String to Double for calculations
)

data class CartItem(
    val product: Product,
    var quantity: Int = 1
)
