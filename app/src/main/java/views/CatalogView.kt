package views

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import components.GradientBackgroundHome
import data.Product
import data.ProductRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import viewmodels.CartViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogView(
        paddingValues: PaddingValues,
        onNavigateToAddProduct: () -> Unit,
        onNavigateToProductDetail: (Int) -> Unit,
        cartViewModel: CartViewModel
) {
    var searchQuery by remember { mutableStateOf("") }
    var sortOption by remember { mutableStateOf("Ordenar por") }
    var products by remember { mutableStateOf(emptyList<Product>()) }
    val coroutineScope = rememberCoroutineScope()

    fun loadProducts() {
        coroutineScope.launch {
            when (val result = ProductRepository.getAllProducts()) {
                is data.network.ApiResult.Success -> products = result.data
                is data.network.ApiResult.Error -> {
                    /* Mostrar error */
                }
                is data.network.ApiResult.Loading -> {
                    /* No usado */
                }
            }
        }
    }

    LaunchedEffect(Unit) { loadProducts() }

    GradientBackgroundHome {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp)) {
                Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                            text = "Productos",
                            style = MaterialTheme.typography.headlineMedium,
                            color = Color.White
                    )

                    // Animación de rotación en el botón de agregar
                    var isRotating by remember { mutableStateOf(false) }
                    val rotation by
                            animateFloatAsState(
                                    targetValue = if (isRotating) 180f else 0f,
                                    animationSpec = tween(300, easing = FastOutSlowInEasing),
                                    label = "addRotation"
                            )

                    IconButton(
                            onClick = {
                                isRotating = !isRotating
                                onNavigateToAddProduct()
                            },
                            modifier = Modifier.graphicsLayer { rotationZ = rotation }
                    ) {
                        Icon(
                                Icons.Default.Add,
                                contentDescription = "Añadir Producto",
                                tint = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        label = { Text("Filtro / Búsqueda") },
                        modifier = Modifier.fillMaxWidth(),
                        colors =
                                TextFieldDefaults.colors(
                                        focusedTextColor = Color.White,
                                        unfocusedTextColor = Color.White,
                                        cursorColor = Color.White,
                                        focusedIndicatorColor = Color.White,
                                        unfocusedIndicatorColor = Color.Gray,
                                        focusedLabelColor = Color.White,
                                        unfocusedLabelColor = Color.Gray,
                                        focusedContainerColor = Color.Transparent,
                                        unfocusedContainerColor = Color.Transparent
                                )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Box {
                    var expanded by remember { mutableStateOf(false) }
                    OutlinedButton(
                            onClick = { expanded = true },
                            modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                                sortOption,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White
                        )
                        Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "Ordenar por",
                                tint = Color.White
                        )
                    }
                    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                        DropdownMenuItem(
                                text = {
                                    Text("Precio", style = MaterialTheme.typography.bodyMedium)
                                },
                                onClick = {
                                    sortOption = "Precio"
                                    expanded = false
                                }
                        )
                        DropdownMenuItem(
                                text = {
                                    Text("Nombre", style = MaterialTheme.typography.bodyMedium)
                                },
                                onClick = {
                                    sortOption = "Nombre"
                                    expanded = false
                                }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    itemsIndexed(
                            products.filter { it.name.contains(searchQuery, ignoreCase = true) }
                    ) { index, product ->
                        // Animación 4: Aparición escalonada de productos
                        var isVisible by remember { mutableStateOf(false) }

                        LaunchedEffect(product.id) {
                            delay(index * 100L)
                            isVisible = true
                        }

                        AnimatedVisibility(
                                visible = isVisible,
                                enter =
                                        fadeIn(animationSpec = tween(400)) +
                                                slideInVertically(
                                                        initialOffsetY = { 50 },
                                                        animationSpec = tween(400)
                                                )
                        ) {
                            ProductItem(
                                    product = product,
                                    onProductClick = { onNavigateToProductDetail(product.id) },
                                    cartViewModel = cartViewModel
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProductItem(product: Product, onProductClick: () -> Unit, cartViewModel: CartViewModel) {
    // Animación 5: Feedback visual al agregar al carrito
    var isPressed by remember { mutableStateOf(false) }
    val scale by
            animateFloatAsState(
                    targetValue = if (isPressed) 0.95f else 1f,
                    animationSpec =
                            spring(
                                    dampingRatio = Spring.DampingRatioMediumBouncy,
                                    stiffness = Spring.StiffnessLow
                            ),
                    label = "buttonScale"
            )

    LaunchedEffect(isPressed) {
        if (isPressed) {
            delay(150)
            isPressed = false
        }
    }

    Card(
            modifier = Modifier.fillMaxWidth().clickable { onProductClick() },
            colors = CardDefaults.cardColors(containerColor = Color.DarkGray.copy(alpha = 0.5f))
    ) {
        Row(
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White
            )
            Button(
                    onClick = {
                        isPressed = true
                        cartViewModel.addToCart(product)
                    },
                    modifier =
                            Modifier.graphicsLayer {
                                scaleX = scale
                                scaleY = scale
                            }
            ) { Text("Añadir al carrito", style = MaterialTheme.typography.labelLarge) }
        }
    }
}
