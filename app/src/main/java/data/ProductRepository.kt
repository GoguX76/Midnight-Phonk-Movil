package data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object ProductRepository {
    // Lista mutable privada para almacenar productos en memoria
    private val products = mutableListOf<Product>()

    //Para obtener todos los productos
    suspend fun getAllProducts(): List<Product> = withContext(Dispatchers.IO) {
        products.toList()
    }

    //Para buscar un producto mediante su ID
    suspend fun findProductByIdl(id: Int): Product? = withContext(Dispatchers.IO) {
        products.find { it.id == id }
    }

    //Para crear un nuevo producto
    suspend fun insertProduct(product: Product) = withContext(Dispatchers.IO) {
        products.add(product)
    }

    //Para actualizar los datos de un producto
    suspend fun updateProduct(product: Product) = withContext(Dispatchers.IO) {
        val index = products.indexOfFirst { it.id == product.id }
        if (index != -1) {
            products[index] = product
        } else {
            false
        }
    }

    //Para eliminar un producto mediante ID
    suspend fun deleteProduct(id: Int): Boolean = withContext(Dispatchers.IO) {
        products.removeIf {it.id == id}
    }

    //Para eliminar todos los productos
    suspend fun clearProducts() = withContext(Dispatchers.IO) {
        products.clear()
    }
}