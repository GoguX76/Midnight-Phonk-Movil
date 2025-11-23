package views

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
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
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(16.dp))

            if (cartViewModel.cartItems.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("El carrito está vacío", style = MaterialTheme.typography.titleLarge, color = Color.White)
                }
            } else {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(cartViewModel.cartItems) { item ->
                        CartItemView(item = item, cartViewModel = cartViewModel)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                // Animación de color cuando cambia el total
                val totalColor by animateColorAsState(
                    targetValue = if (cartViewModel.total > 0) Color(0xFFFFD700) else Color.White,
                    animationSpec = tween(500),
                    label = "totalColor"
                )

                Text(
                    text = "Total: $${cartViewModel.total}",
                    style = MaterialTheme.typography.headlineSmall,
                    color = totalColor,
                    modifier = Modifier.align(Alignment.End)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { cartViewModel.completePurchase(onNavigateToResult) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Realizar Compra", style = MaterialTheme.typography.labelLarge)
                }
            }
        }
    }
}

@Composable
fun CartItemView(item: CartItem, cartViewModel: CartViewModel) {
    // Animación 6: Animación de cantidad cuando cambia
    var currentQuantity by remember { mutableStateOf(item.quantity) }
    val quantityScale by animateFloatAsState(
        targetValue = if (currentQuantity != item.quantity) 1.3f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessHigh
        ),
        label = "quantityScale"
    )

    LaunchedEffect(item.quantity) {
        currentQuantity = item.quantity
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            ),
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
                Text(item.product.title, style = MaterialTheme.typography.titleMedium, color = Color.White)
                Text("Precio: $${item.product.price}", style = MaterialTheme.typography.bodySmall, color = Color.White)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Button(onClick = { cartViewModel.updateQuantity(item, item.quantity - 1) }) {
                    Text("-", style = MaterialTheme.typography.labelLarge)
                }
                Text(
                    text = "${item.quantity}",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .graphicsLayer {
                            scaleX = quantityScale
                            scaleY = quantityScale
                        }
                )
                Button(onClick = { cartViewModel.updateQuantity(item, item.quantity + 1) }) {
                    Text("+", style = MaterialTheme.typography.labelLarge)
                }
            }
        }
    }
}

