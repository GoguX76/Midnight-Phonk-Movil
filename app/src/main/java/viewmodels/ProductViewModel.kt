
package viewmodels

import android.app.Application
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import data.Product
import data.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class ProductViewModel(application: Application) : AndroidViewModel(application) {
    var productName by mutableStateOf("")
    var productDescription by mutableStateOf("")
    var productPrice by mutableStateOf("")
    var productStock by mutableStateOf("")
    var productCategory by mutableStateOf("Categoría")
    var productImageUri by mutableStateOf<Uri?>(null)

    private fun saveImageToInternalStorage(uri: Uri): String {
        val context = getApplication<Application>().applicationContext
        val inputStream = context.contentResolver.openInputStream(uri)!!
        val file = File(context.filesDir, "${System.currentTimeMillis()}_product_image.jpg")
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)
        return file.absolutePath
    }

    fun saveProduct(onProductSaved: () -> Unit) {
        viewModelScope.launch {
            val imageUrl = withContext(Dispatchers.IO) {
                productImageUri?.let { saveImageToInternalStorage(it) }
            } ?: ""

            val newProduct = Product(
                name = productName,
                description = productDescription,
                price = productPrice.toDoubleOrNull() ?: 0.0,
                imageUrl = imageUrl,
                stock = productStock.toIntOrNull() ?: 0,
                category = productCategory
            )
            ProductRepository.insertProduct(newProduct)
            onProductSaved()
        }
    }
}
