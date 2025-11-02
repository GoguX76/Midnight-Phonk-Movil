
package views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import components.GradientBackgroundHome
import data.CartItem
import viewmodels.CartViewModel
import java.util.Locale

@Composable
fun CartView(paddingValues: PaddingValues, cartViewModel: CartViewModel) {
    val cartItems = cartViewModel.cartItems

    GradientBackgroundHome {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            if (cartItems.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Tu carrito está vacío", fontSize = 20.sp, color = Color.White)
                }
            } else {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(cartItems) { item ->
                        CartItemRow(item = item, viewModel = cartViewModel)
                        HorizontalDivider(color = Color.Gray)
                    }
                }
                CartSummary(viewModel = cartViewModel)
            }
        }
    }
}

@Composable
fun CartItemRow(item: CartItem, viewModel: CartViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(item.product.name, fontSize = 16.sp, color = Color.White)
            Text("$${item.product.price}", fontSize = 14.sp, color = Color.Gray)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { viewModel.updateQuantity(item, item.quantity - 1) }) {
                Text("-", color = Color.White, fontSize = 20.sp)
            }
            Text("${item.quantity}", color = Color.White, modifier = Modifier.padding(horizontal = 8.dp))
            IconButton(onClick = { viewModel.updateQuantity(item, item.quantity + 1) }) {
                Text("+", color = Color.White, fontSize = 20.sp)
            }
            TextButton(onClick = { viewModel.removeFromCart(item) }) {
                Text("Eliminar", color = Color.Red)
            }
        }
    }
}

@Composable
fun CartSummary(viewModel: CartViewModel) {
    val subtotal = viewModel.getTotal()
    val shipping = if (subtotal > 0) 5.0 else 0.0 // Example shipping cost
    val total = subtotal + shipping

    Column(modifier = Modifier.fillMaxWidth()) {
        HorizontalDivider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Subtotal", color = Color.White)
            Text("$${String.format(Locale.US, "%.2f", subtotal)}", color = Color.White)
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Envío", color = Color.White)
            Text("$${String.format(Locale.US, "%.2f", shipping)}", color = Color.White)
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Total", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text("$${String.format(Locale.US, "%.2f", total)}", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { /* TODO: Implement Payment */ },
            modifier = Modifier.fillMaxWidth(),
            enabled = viewModel.cartItems.isNotEmpty()
        ) {
            Text("Pagar")
        }
    }
}
