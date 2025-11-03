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
            // 1. Verificar el stock
            for (item in _cartItems) {
                val productInRepo = ProductRepository.findProductByIdl(item.product.id)
                if (productInRepo == null || item.quantity > productInRepo.stock) {
                    purchaseResult = "No hay suficiente stock para ${item.product.name}. Compra rechazada."
                    onPurchaseCompleted() // Navega a la pantalla de resultados (incluso si falla)
                    return@launch
                }
            }

            // 2. Actualizar el repositorio si hay stock
            for (item in _cartItems) {
                val productInRepo = ProductRepository.findProductByIdl(item.product.id)!!
                val updatedProduct = productInRepo.copy(stock = productInRepo.stock - item.quantity)
                ProductRepository.updateProduct(updatedProduct)
            }

            // 3. Limpiar el carrito y establecer el mensaje de éxito
            _cartItems.clear()
            purchaseResult = "¡Compra realizada correctamente!"

            // 4. Ejecutar el callback para navegar
            onPurchaseCompleted()
        }
    }

    fun clearPurchaseResult() {
        purchaseResult = null
    }
}