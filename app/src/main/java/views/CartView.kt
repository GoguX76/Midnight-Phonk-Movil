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

@Composable
fun CartView(
    paddingValues: PaddingValues,
    cartViewModel: CartViewModel,
    onNavigateToResult: () -> Unit
) {
    GradientBackgroundHome {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(
                text = "Carrito de Compras",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(16.dp))

            if (cartViewModel.cartItems.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("El carrito está vacío", color = Color.White, fontSize = 20.sp)
                }
            } else {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(cartViewModel.cartItems) { item ->
                        CartItemView(item = item, cartViewModel = cartViewModel)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Total: $${cartViewModel.total}",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.End)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { cartViewModel.completePurchase(onNavigateToResult) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Realizar Compra")
                }
            }
        }
    }
}

@Composable
fun CartItemView(item: CartItem, cartViewModel: CartViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.DarkGray.copy(alpha = 0.5f))
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(item.product.name, color = Color.White, fontSize = 18.sp)
                Text("Precio: $${item.product.price}", color = Color.White, fontSize = 14.sp)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Button(onClick = { cartViewModel.updateQuantity(item, item.quantity - 1) }) {
                    Text("-")
                }
                Text(
                    text = "${item.quantity}",
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 8.dp),
                    fontSize = 18.sp
                )
                Button(onClick = { cartViewModel.updateQuantity(item, item.quantity + 1) }) {
                    Text("+")
                }
            }
        }
    }
}
