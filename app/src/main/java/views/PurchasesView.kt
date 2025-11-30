package views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import components.GradientBackgroundHome
import data.Purchase
import viewmodels.AccountViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PurchasesView(navController: NavController, viewModel: AccountViewModel) {
    
    LaunchedEffect(Unit) {
        viewModel.getPurchases()
    }

    GradientBackgroundHome {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    title = { Text("Mis Compras", color = Color.White) },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = Color.White)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent
                    )
                )
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                if (viewModel.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                } else if (viewModel.errorMessage != null) {
                    Text(
                        text = viewModel.errorMessage ?: "Error desconocido",
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.Center)
                    )
                } else if (viewModel.purchases.isEmpty()) {
                    Text(
                        text = "No tienes compras registradas",
                        color = Color.White,
                        modifier = Modifier.align(Alignment.Center)
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(viewModel.purchases) { purchase ->
                            PurchaseItem(purchase)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PurchaseItem(purchase: Purchase) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.Black.copy(alpha = 0.6f)
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(text = "Compra #${purchase.id}", style = MaterialTheme.typography.titleMedium, color = Color.White)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Fecha: ${purchase.purchaseDate}", color = Color.Gray)
            Text(text = "Total: $${purchase.totalPrice}", color = Color.Green)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Producto ID: ${purchase.productId}", color = Color.LightGray)
            Text(text = "Cantidad: ${purchase.quantity}", color = Color.LightGray)
        }
    }
}
