package viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.Purchase
import data.UserSession
import data.network.RetrofitClient
import kotlinx.coroutines.launch

class AccountViewModel : ViewModel() {
    var purchases by mutableStateOf<List<Purchase>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var updateSuccess by mutableStateOf<String?>(null)
        private set

    fun updateName(newName: String) {
        val currentUser = UserSession.currentUser ?: return
        isLoading = true
        errorMessage = null
        updateSuccess = null

        viewModelScope.launch {
            try {
                // Create a copy of the user with the new name
                // Note: The API expects the full user object for updates usually, 
                // or at least the fields that are not null.
                // Based on UserController.java: 
                // user.setName(userDetails.getName());
                // user.setEmail(userDetails.getEmail());
                // user.setPassword(userDetails.getPassword());
                
                val updatedUser = currentUser.copy(name = newName)
                val response = RetrofitClient.apiService.updateUser(currentUser.id, updatedUser)
                
                // Update local session
                UserSession.currentUser = response
                updateSuccess = "Nombre actualizado correctamente"
            } catch (e: Exception) {
                errorMessage = "Error al actualizar nombre: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

    fun updatePassword(newPassword: String) {
        val currentUser = UserSession.currentUser ?: return
        isLoading = true
        errorMessage = null
        updateSuccess = null

        viewModelScope.launch {
            try {
                val updatedUser = currentUser.copy(password = newPassword)
                val response = RetrofitClient.apiService.updateUser(currentUser.id, updatedUser)
                
                // Update local session
                UserSession.currentUser = response
                updateSuccess = "Contraseña actualizada correctamente"
            } catch (e: Exception) {
                errorMessage = "Error al actualizar contraseña: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

    fun getPurchases() {
        val currentUser = UserSession.currentUser ?: return
        isLoading = true
        errorMessage = null

        viewModelScope.launch {
            try {
                purchases = RetrofitClient.apiService.getPurchasesByUserId(currentUser.id)
            } catch (e: Exception) {
                errorMessage = "Error al obtener compras: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }
    
    fun clearMessages() {
        errorMessage = null
        updateSuccess = null
    }
}
