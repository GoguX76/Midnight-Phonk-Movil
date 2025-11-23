package viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.CartItem
import data.Product
import data.ProductRepository
import data.network.ApiResult
import kotlinx.coroutines.launch

class CartViewModel : ViewModel() {
    private val _cartItems = mutableStateListOf<CartItem>()
    val cartItems: List<CartItem> = _cartItems

    var purchaseResult by mutableStateOf<String?>(null)
        private set

    val total: Double
        get() = _cartItems.sumOf { it.product.price * it.quantity }

    fun addToCart(product: Product) {
        val existingItem = _cartItems.find { it.product.id == product.id }
        if (existingItem != null) {
            if (existingItem.quantity < product.stock) {
                existingItem.quantity++
            }
        } else {
            if (product.stock > 0) {
                _cartItems.add(CartItem(product = product))
            }
        }
    }

    fun removeFromCart(item: CartItem) {
        _cartItems.remove(item)
    }

    fun updateQuantity(item: CartItem, newQuantity: Int) {
        if (newQuantity > 0) {
            if (newQuantity <= item.product.stock) {
                val index = _cartItems.indexOf(item)
                if (index != -1) {
                    _cartItems[index] = item.copy(quantity = newQuantity)
                }
            }
        } else {
            removeFromCart(item)
        }
    }

    fun completePurchase(onPurchaseCompleted: () -> Unit) {
        viewModelScope.launch {
            // Verificar stock de cada producto
            val productsToCheck =
                    _cartItems.map { item ->
                        ProductRepository.findProductById(item.product.id.toLong()) to item
                    }

            // Verificar que todos los productos se obtuvieron correctamente
            for ((productResult, cartItem) in productsToCheck) {
                when (productResult) {
                    is ApiResult.Success -> {
                        val product = productResult.data
                        if (cartItem.quantity > product.stock) {
                            purchaseResult =
                                    "No hay suficiente stock para ${product.title}. Compra rechazada."
                            onPurchaseCompleted()
                            return@launch
                        }
                    }
                    is ApiResult.Error -> {
                        purchaseResult = "Error al verificar stock: ${productResult.message}"
                        onPurchaseCompleted()
                        return@launch
                    }
                    is ApiResult.Loading -> {
                        /* No usado */
                    }
                }
            }

            // Actualizar stock de cada producto
            for ((productResult, item) in productsToCheck) {
                if (productResult is ApiResult.Success) {
                    val product = productResult.data
                    val updatedProduct = product.copy(stock = product.stock - item.quantity)

                    when (val updateResult = ProductRepository.updateProduct(updatedProduct)) {
                        is ApiResult.Success -> {
                            /* Continuar */
                        }
                        is ApiResult.Error -> {
                            purchaseResult = "Error al actualizar stock: ${updateResult.message}"
                            onPurchaseCompleted()
                            return@launch
                        }
                        is ApiResult.Loading -> {
                            /* No usado */
                        }
                    }
                }
            }

            // Limpiar carrito y mostrar éxito
            _cartItems.clear()
            purchaseResult = "¡Compra realizada correctamente!"
            onPurchaseCompleted()
        }
    }

    fun clearPurchaseResult() {
        purchaseResult = null
    }
}
