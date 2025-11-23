package data

import com.google.gson.annotations.SerializedName

data class Product(
    val id: Int = 0,
    val title: String,
    val price: Double,
    @SerializedName("desc")
    val description: String,
    @SerializedName("fullDesc")
    val fullDesc: String,
    val image: String = "",
    val stock: Int = 0,
    val category: String = ""
)
