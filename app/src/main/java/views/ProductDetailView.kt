package views

import androidx.compose.foundation.layout.* 
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = p.description, fontSize = 16.sp, color = Color.White)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Categoría: ${p.category}", fontSize = 16.sp, color = Color.White)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Precio: $${p.price}", fontSize = 20.sp, color = Color.White)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "En stock: ${p.stock}", fontSize = 16.sp, color = Color.White)
                    Spacer(modifier = Modifier.weight(1f))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(onClick = { cartViewModel.addToCart(p) }) {
                            Text("Añadir al carrito")
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
                            Text("Eliminar producto")
                        }
                    }
                }
            }
        }
    }
}
