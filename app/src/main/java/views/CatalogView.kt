package views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import components.GradientBackgroundHome
import data.Product
import data.ProductRepository
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
            products = ProductRepository.getAllProducts()
        }
    }

    LaunchedEffect(Unit) {
        loadProducts()
    }

    GradientBackgroundHome {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Productos",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    IconButton(onClick = { onNavigateToAddProduct() }) {
                        Icon(Icons.Default.Add, contentDescription = "Añadir Producto", tint = Color.White)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    label = { Text("Filtro / Búsqueda") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
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
                        Text(sortOption, color = Color.White)
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Ordenar por",
                            tint = Color.White
                        )
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Precio") },
                            onClick = { sortOption = "Precio"; expanded = false }
                        )
                        DropdownMenuItem(
                            text = { Text("Nombre") },
                            onClick = { sortOption = "Nombre"; expanded = false }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(products.filter { it.name.contains(searchQuery, ignoreCase = true) }) { product ->
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

@Composable
fun ProductItem(
    product: Product,
    onProductClick: () -> Unit,
    cartViewModel: CartViewModel
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onProductClick() },
        colors = CardDefaults.cardColors(containerColor = Color.DarkGray.copy(alpha = 0.5f))
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = product.name, color = Color.White, fontSize = 16.sp)
            Button(onClick = { cartViewModel.addToCart(product) }) {
                Text("Añadir al carrito")
            }
        }
    }
}
