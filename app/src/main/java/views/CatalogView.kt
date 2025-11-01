
package views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
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


data class Product(val id: Int, val name: String)

val productList = listOf(
    Product(1, "Item Producto 1"),
    Product(2, "Item Producto 2"),
    Product(3, "Item Producto 3"),
    Product(4, "Item Producto 4"),
    Product(5, "Item Producto 5"),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogView(paddingValues: PaddingValues) {
    var searchQuery by remember { mutableStateOf("") }
    var sortOption by remember { mutableStateOf("Ordenar por") }

    GradientBackgroundHome {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(
                text = "Productos",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Filter/Search bar
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

            // Sort dropdown
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
                        onClick = {
                            sortOption = "Precio"
                            expanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Nombre") },
                        onClick = {
                            sortOption = "Nombre"
                            expanded = false
                        }
                    )
                }
            }


            Spacer(modifier = Modifier.height(16.dp))

            // Product list
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(productList.filter { it.name.contains(searchQuery, ignoreCase = true) }) { product ->
                    ProductItem(product = product)
                }
            }
        }
    }
}

@Composable
fun ProductItem(product: Product) {
    Card(
        modifier = Modifier.fillMaxWidth(),
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
            Button(onClick = { /* Add to cart */ }) {
                Text("Agregar +")
            }
        }
    }
}
