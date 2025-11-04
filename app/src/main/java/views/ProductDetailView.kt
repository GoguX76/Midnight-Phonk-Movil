package views

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import components.GradientBackgroundHome
import data.Product
import data.ProductRepository
import kotlinx.coroutines.launch
import viewmodels.CartViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailView(
    productId: Int,
    onNavigateUp: () -> Unit,
    onProductDeleted: () -> Unit,
    cartViewModel: CartViewModel
) {
    var product by remember { mutableStateOf<Product?>(null) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(productId) {
        product = ProductRepository.findProductByIdl(productId)
    }

    GradientBackgroundHome {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(product?.name ?: "") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFF6A0DAD),
                        titleContentColor = Color.White
                    ),
                    navigationIcon = {
                        IconButton(onClick = onNavigateUp) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.White
                            )
                        }
                    }
                )
            },
            containerColor = Color.Transparent
        ) { paddingValues ->
            product?.let { p ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp)
                ) {
                    AsyncImage(
                        model = p.imageUrl,
                        contentDescription = "Product Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = p.name,
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = p.description, style = MaterialTheme.typography.bodyMedium, color = Color.White)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Categoría: ${p.category}", style = MaterialTheme.typography.bodyMedium, color = Color.White)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Precio: $${p.price}", style = MaterialTheme.typography.titleLarge, color = Color.White)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "En stock: ${p.stock}", style = MaterialTheme.typography.bodySmall, color = Color.White)
                    Spacer(modifier = Modifier.weight(1f))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(onClick = { cartViewModel.addToCart(p) }) {
                            Text("Añadir al carrito", style = MaterialTheme.typography.labelLarge)
                        }
                        Button(
                            onClick = {
                                coroutineScope.launch {
                                    ProductRepository.deleteProduct(p.id)
                                    onProductDeleted()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                        ) {
                            Text("Eliminar producto", style = MaterialTheme.typography.labelLarge)
                        }
                    }
                }
            }
        }
    }
}

