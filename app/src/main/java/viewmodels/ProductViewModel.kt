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
import data.network.ApiResult
import java.io.File
import java.io.FileOutputStream
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductViewModel(application: Application) : AndroidViewModel(application) {
    var productName by mutableStateOf("")
    var productDescription by mutableStateOf("")
    var productPrice by mutableStateOf("")
    var productStock by mutableStateOf("")
    var productCategory by mutableStateOf("Categoría")
    var productImageUri by mutableStateOf<Uri?>(null)

    var errorMessage by mutableStateOf<String?>(null)
    var isLoading by mutableStateOf(false)

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
            isLoading = true
            errorMessage = null

            val imageUrl =
                    withContext(Dispatchers.IO) {
                        productImageUri?.let { saveImageToInternalStorage(it) }
                    }
                            ?: ""

            val newProduct =
                    Product(
                            name = productName,
                            description = productDescription,
                            price = productPrice.toDoubleOrNull() ?: 0.0,
                            imageUrl = imageUrl,
                            stock = productStock.toIntOrNull() ?: 0,
                            category = productCategory
                    )

            when (val result = ProductRepository.insertProduct(newProduct)) {
                is ApiResult.Success -> {
                    isLoading = false
                    onProductSaved()
                }
                is ApiResult.Error -> {
                    isLoading = false
                    errorMessage = result.message
                }
                is ApiResult.Loading -> {
                    /* No usado aquí */
                }
            }
        }
    }

    fun loadProduct(product: Product) {
        productName = product.name
        productDescription = product.description
        productPrice = product.price.toString()
        productStock = product.stock.toString()
        productCategory = product.category
        productImageUri = null
    }

    fun updateProduct(productId: Int, onProductUpdated: () -> Unit) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            // Obtener producto existente
            val existingProductResult = ProductRepository.findProductById(productId.toLong())

            val imageUrl =
                    withContext(Dispatchers.IO) {
                        if (productImageUri != null) {
                            saveImageToInternalStorage(productImageUri!!)
                        } else {
                            when (existingProductResult) {
                                is ApiResult.Success -> existingProductResult.data.imageUrl
                                else -> ""
                            }
                        }
                    }

            val updatedProduct =
                    Product(
                            id = productId,
                            name = productName,
                            description = productDescription,
                            price = productPrice.toDoubleOrNull() ?: 0.0,
                            imageUrl = imageUrl,
                            stock = productStock.toIntOrNull() ?: 0,
                            category = productCategory
                    )

            when (val result = ProductRepository.updateProduct(updatedProduct)) {
                is ApiResult.Success -> {
                    isLoading = false
                    onProductUpdated()
                }
                is ApiResult.Error -> {
                    isLoading = false
                    errorMessage = result.message
                }
                is ApiResult.Loading -> {
                    /* No usado aquí */
                }
            }
        }
    }
}
