package data

data class Product(
    val id: Int = 0,
    val name: String,
    val price: Double,
    val description: String = "",
    val imageUrl: String = "",
    val stock: Int = 0,
    val category: String = ""
)
