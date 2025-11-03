package data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

object ProductRepository {
    private val products = mutableListOf<Product>()
    private val gson = Gson()
    private lateinit var productsFile: File

    private var productsLoaded = false

    // Debe llamarse desde la clase Application para inicializar el repositorio.
    fun initialize(context: Context) {
        productsFile = File(context.filesDir, "products.json")
    }

    private fun loadProductsFromFile() {
        if (productsFile.exists()) {
            val json = productsFile.readText()
            if (json.isNotBlank()) {
                val type = object : TypeToken<MutableList<Product>>() {}.type
                val loadedProducts: MutableList<Product>? = gson.fromJson(json, type)
                if (loadedProducts != null) {
                    products.clear()
                    products.addAll(loadedProducts)
                }
            }
        }
        productsLoaded = true
    }

    private fun saveProductsToFile() {
        val json = gson.toJson(products)
        productsFile.writeText(json)
    }

    //Para obtener todos los productos
    suspend fun getAllProducts(): List<Product> = withContext(Dispatchers.IO) {
        if (!productsLoaded) {
            loadProductsFromFile()
        }
        products.toList()
    }

    //Para buscar un producto mediante su ID
    suspend fun findProductByIdl(id: Int): Product? = withContext(Dispatchers.IO) {
        if (!productsLoaded) {
            loadProductsFromFile()
        }
        products.find { it.id == id }
    }

    //Para crear un nuevo producto
    suspend fun insertProduct(product: Product) = withContext(Dispatchers.IO) {
        if (!productsLoaded) {
            loadProductsFromFile()
        }
        // Generar un nuevo ID único
        val newId = (products.maxOfOrNull { it.id } ?: 0) + 1
        val newProduct = product.copy(id = newId)

        products.add(newProduct)
        saveProductsToFile()
    }

    //Para actualizar los datos de un producto
    suspend fun updateProduct(product: Product) = withContext(Dispatchers.IO) {
        if (!productsLoaded) {
            loadProductsFromFile()
        }
        val index = products.indexOfFirst { it.id == product.id }
        if (index != -1) {
            products[index] = product
            saveProductsToFile()
        }
    }

    //Para eliminar un producto mediante ID
    suspend fun deleteProduct(id: Int): Boolean = withContext(Dispatchers.IO) {
        if (!productsLoaded) {
            loadProductsFromFile()
        }
        val removed = products.removeIf {it.id == id}
        if (removed) {
            saveProductsToFile()
        }
        removed
    }

    //Para eliminar todos los productos
    suspend fun clearProducts() = withContext(Dispatchers.IO) {
        products.clear()
        saveProductsToFile()
    }
}