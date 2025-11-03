package data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

object UserRepository {
    private val users = mutableListOf<User>()
    private val gson = Gson()
    private lateinit var usersFile: File

    private var usersLoaded = false

    // Debe llamarse desde la clase Application para inicializar el repositorio.
    fun initialize(context: Context) {
        usersFile = File(context.filesDir, "users.json")
    }

    private fun loadUsersFromFile() {
        if (usersFile.exists()) {
            val json = usersFile.readText()
            if (json.isNotBlank()) {
                val type = object : TypeToken<MutableList<User>>() {}.type
                val loadedUsers: MutableList<User>? = gson.fromJson(json, type)
                if (loadedUsers != null) {
                    users.clear()
                    users.addAll(loadedUsers)
                }
            }
        }
        usersLoaded = true
    }

    private fun saveUsersToFile() {
        val json = gson.toJson(users)
        usersFile.writeText(json)
    }

    suspend fun findUserByEmail(email: String): User? = withContext(Dispatchers.IO) {
        if (!usersLoaded) {
            loadUsersFromFile()
        }
        users.find { it.email == email }
    }

    suspend fun insertUser(user: User) = withContext(Dispatchers.IO) {
        if (!usersLoaded) {
            loadUsersFromFile()
        }
        // Generar un nuevo ID único
        val newId = (users.maxOfOrNull { it.id } ?: 0) + 1
        val newUser = user.copy(id = newId)

        users.add(newUser)
        saveUsersToFile()
    }
}