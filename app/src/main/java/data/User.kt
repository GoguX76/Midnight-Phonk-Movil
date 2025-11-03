package data

// Modelo simple de datos sin Room
data class User(
    val id: Int = 0,
    val email: String,
    val passwordHash: String,
    val name: String = ""
)