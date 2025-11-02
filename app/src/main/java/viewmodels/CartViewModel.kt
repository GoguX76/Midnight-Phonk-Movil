
package viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import data.CartItem
import data.Product

class CartViewModel : ViewModel() {
    private val _cartItems = mutableStateOf<List<CartItem>>(emptyList())
    val cartItems: List<CartItem> get() = _cartItems.value

    fun addToCart(product: Product) {
        val existingItem = _cartItems.value.find { it.product.id == product.id }
        if (existingItem != null) {
            updateQuantity(existingItem, existingItem.quantity + 1)
        } else {
            _cartItems.value = _cartItems.value + CartItem(product = product)
        }
    }

    fun removeFromCart(item: CartItem) {
        _cartItems.value = _cartItems.value.filter { it.product.id != item.product.id }
    }

    fun updateQuantity(item: CartItem, newQuantity: Int) {
        if (newQuantity > 0) {
            val updatedList = _cartItems.value.map {
                if (it.product.id == item.product.id) {
                    it.copy(quantity = newQuantity)
                } else {
                    it
                }
            }
            _cartItems.value = updatedList
        } else {
            removeFromCart(item)
        }
    }

    fun getTotal(): Double {
        return _cartItems.value.sumOf { it.product.price * it.quantity }
    }
}
