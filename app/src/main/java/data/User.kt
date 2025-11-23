package data

import com.google.gson.annotations.SerializedName

// Modelo simple de datos sin Room
data class User(
    val id: Int = 0,
    val email: String,
    @SerializedName("password")
    val password: String,
    val name: String
)