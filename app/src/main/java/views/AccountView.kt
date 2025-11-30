package views

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import components.GradientBackgroundHome
import data.UserSession
import navigation.AppScreens
import viewmodels.AccountViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountView(navController: NavController, viewModel: AccountViewModel = viewModel()) {
    val user = UserSession.currentUser
    var showNameDialog by remember { mutableStateOf(false) }
    var showPasswordDialog by remember { mutableStateOf(false) }

    var newName by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    if (viewModel.updateSuccess != null) {
        AlertDialog(
            onDismissRequest = { viewModel.clearMessages() },
            title = { Text("Éxito") },
            text = { Text(viewModel.updateSuccess!!) },
            confirmButton = {
                Button(onClick = { viewModel.clearMessages() }) {
                    Text("OK")
                }
            }
        )
    }

    if (viewModel.errorMessage != null) {
        AlertDialog(
            onDismissRequest = { viewModel.clearMessages() },
            title = { Text("Error") },
            text = { Text(viewModel.errorMessage!!) },
            confirmButton = {
                Button(onClick = { viewModel.clearMessages() }) {
                    Text("OK")
                }
            }
        )
    }

    GradientBackgroundHome {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Mi Cuenta", color = Color.White) },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent
                    ),
                )
            },
            containerColor = Color.Transparent
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "Hola, ${user?.name ?: "Usuario"}", style = MaterialTheme.typography.headlineMedium, color = Color.White)
                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        newName = user?.name ?: ""
                        showNameDialog = true
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Cambiar nombre")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        newPassword = ""
                        confirmPassword = ""
                        showPasswordDialog = true
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Cambiar contraseña")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { navController.navigate(AppScreens.Purchases.route) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Consultar compras")
                }

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        UserSession.currentUser = null
                        navController.navigate("login") {
                            popUpTo("main") { inclusive = true }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Cerrar sesión")
                }
            }
        }
    }


    if (showNameDialog) {
        AlertDialog(
            onDismissRequest = { showNameDialog = false },
            title = { Text("Cambiar Nombre") },
            text = {
                OutlinedTextField(
                    value = newName,
                    onValueChange = { newName = it },
                    label = { Text("Nuevo nombre") }
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.updateName(newName)
                        showNameDialog = false
                    }
                ) {
                    Text("Guardar")
                }
            },
            dismissButton = {
                Button(onClick = { showNameDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    if (showPasswordDialog) {
        AlertDialog(
            onDismissRequest = { showPasswordDialog = false },
            title = { Text("Cambiar Contraseña") },
            text = {
                Column {
                    OutlinedTextField(
                        value = newPassword,
                        onValueChange = { newPassword = it },
                        label = { Text("Nueva contraseña") },
                        visualTransformation = PasswordVisualTransformation()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        label = { Text("Confirmar contraseña") },
                        visualTransformation = PasswordVisualTransformation()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (newPassword == confirmPassword && newPassword.isNotEmpty()) {
                            viewModel.updatePassword(newPassword)
                            showPasswordDialog = false
                        }
                    },
                    enabled = newPassword.isNotEmpty() && newPassword == confirmPassword
                ) {
                    Text("Guardar")
                }
            },
            dismissButton = {
                Button(onClick = { showPasswordDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}
