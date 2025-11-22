package views

import android.app.Application
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import components.GradientBackgroundHome
import data.ProductRepository
import viewmodels.ProductViewModel
import viewmodels.ProductViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProductView(productId: Int, onProductUpdated: () -> Unit, onNavigateUp: () -> Unit) {
    val application = LocalContext.current.applicationContext as Application
    val productViewModel: ProductViewModel =
            viewModel(factory = ProductViewModelFactory(application))

    LaunchedEffect(productId) {
        when (val result = ProductRepository.findProductById(productId.toLong())) {
            is data.network.ApiResult.Success -> {
                productViewModel.loadProduct(result.data)
            }
            is data.network.ApiResult.Error -> {
                /* Mostrar error */
            }
            is data.network.ApiResult.Loading -> {
                /* No usado */
            }
        }
    }

    var categoryExpanded by remember { mutableStateOf(false) }
    val categories = listOf("Sample Packs", "Sound Kits", "Plugins")

    val imagePickerLauncher =
            rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.GetContent(),
                    onResult = { uri -> productViewModel.productImageUri = uri }
            )

    GradientBackgroundHome {
        Scaffold(
                topBar = {
                    TopAppBar(
                            title = { Text("Editar producto") },
                            colors =
                                    TopAppBarDefaults.topAppBarColors(
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
            Column(
                    modifier =
                            Modifier.fillMaxSize()
                                    .padding(paddingValues)
                                    .padding(16.dp)
                                    .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                        value = productViewModel.productName,
                        onValueChange = { productViewModel.productName = it },
                        label = { Text("Nombre *") },
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
                OutlinedTextField(
                        value = productViewModel.productDescription,
                        onValueChange = { productViewModel.productDescription = it },
                        label = { Text("Descripción") },
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
                OutlinedTextField(
                        value = productViewModel.productPrice,
                        onValueChange = { productViewModel.productPrice = it },
                        label = { Text("Precio *") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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
                OutlinedTextField(
                        value = productViewModel.productStock,
                        onValueChange = { productViewModel.productStock = it },
                        label = { Text("Stock") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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

                // Category Dropdown
                Box {
                    OutlinedButton(
                            onClick = { categoryExpanded = true },
                            modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                                productViewModel.productCategory,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White
                        )
                        Spacer(Modifier.weight(1f))
                        Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "Category",
                                tint = Color.White
                        )
                    }
                    DropdownMenu(
                            expanded = categoryExpanded,
                            onDismissRequest = { categoryExpanded = false },
                            modifier = Modifier.fillMaxWidth()
                    ) {
                        categories.forEach { cat ->
                            DropdownMenuItem(
                                    text = {
                                        Text(cat, style = MaterialTheme.typography.bodyMedium)
                                    },
                                    onClick = {
                                        productViewModel.productCategory = cat
                                        categoryExpanded = false
                                    }
                            )
                        }
                    }
                }

                // Photo Picker
                OutlinedButton(
                        onClick = { imagePickerLauncher.launch("image/*") },
                        modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                            productViewModel.productImageUri?.let { "Imagen seleccionada" }
                                    ?: "Foto (Picker)",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White
                    )
                }

                Spacer(Modifier.weight(1f))

                // Save/Cancel Buttons
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    TextButton(onClick = onNavigateUp) {
                        Text("Cancelar", style = MaterialTheme.typography.labelLarge)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                            onClick = {
                                productViewModel.updateProduct(productId, onProductUpdated)
                            }
                    ) { Text("Actualizar", style = MaterialTheme.typography.labelLarge) }
                }
            }
        }
    }
}
