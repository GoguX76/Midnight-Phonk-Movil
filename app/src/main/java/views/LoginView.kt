package views

import com.example.midnightphonk.R
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import viewmodels.LoginViewModel
import kotlinx.coroutines.launch
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.clickable
import androidx.lifecycle.viewmodel.compose.viewModel
import components.GradientBackground

@Composable
fun AppTextFieldColorsLogin() = OutlinedTextFieldDefaults.colors(
    focusedTextColor = Color.White,
    unfocusedTextColor = Color.White,
    cursorColor = Color.White,
    focusedBorderColor = Color.White,
    unfocusedBorderColor = Color.Gray,
    focusedLabelColor = Color.White,
    unfocusedLabelColor = Color.Gray
)

@Composable
fun AppButtonColorsLogin() = ButtonDefaults.buttonColors(
    containerColor = Color(0xFF6A0DAD),
    contentColor = Color.White,
    disabledContainerColor = Color.DarkGray,
    disabledContentColor = Color.Gray
)

@Composable
fun LoginView(
    onNavigateToRegister: () -> Unit,
    onNavigateToHome: () -> Unit,
    viewModel: LoginViewModel = viewModel()
) {
    var showError by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()

    // Animación de shake para errores
    var shakeError by remember { mutableStateOf(false) }
    val shakeOffset by animateFloatAsState(
        targetValue = if (shakeError) 10f else 0f,
        animationSpec = repeatable(
            iterations = 3,
            animation = tween(50),
            repeatMode = RepeatMode.Reverse
        ),
        finishedListener = { shakeError = false },
        label = "shakeOffset"
    )

    GradientBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.midnight_phonk_logo),
                contentDescription = "Midnight Phonk Logo",
                modifier = Modifier
                    .size(200.dp)
                    .padding(bottom = 24.dp)
            )

            Text(
                text = "Midnight Phonk",
                style = MaterialTheme.typography.displayMedium,
                color = Color.White,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            OutlinedTextField(
                value = viewModel.email,
                onValueChange = { viewModel.updateEmail(it) },
                label = { Text("Ingrese su correo") },
                modifier = Modifier.fillMaxWidth(),
                colors = AppTextFieldColorsLogin()
            )

            if (viewModel.emailError != null) {
                Text(
                    text = viewModel.emailError!!,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Red,
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = viewModel.password,
                onValueChange = { viewModel.updatePassword(it) },
                label = { Text("Ingrese su contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                isError = viewModel.passwordError != null,
                modifier = Modifier.fillMaxWidth(),
                colors = AppTextFieldColorsLogin()
            )

            if (viewModel.passwordError != null) {
                Text(
                    text = viewModel.passwordError!!,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Red,
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    coroutineScope.launch {
                        val result = viewModel.loginUser()
                        when (result) {
                            is viewmodels.LoginResult.Success -> {
                                showError = null
                                onNavigateToHome()
                            }
                            is viewmodels.LoginResult.Error -> {
                                showError = result.message
                                shakeError = true
                            }
                        }
                    }
                },
                enabled = viewModel.isValid(),
                modifier = Modifier
                    .fillMaxWidth()
                    .graphicsLayer {
                        translationX = if (showError != null) shakeOffset else 0f
                    },
                colors = AppButtonColorsLogin()
            ) {
                Text("Iniciar sesión", style = MaterialTheme.typography.labelLarge)
            }

            if (showError != null) {
                Text(
                    text = showError!!,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Red,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "¿No tienes una cuenta? Crea una aquí",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .padding(16.dp)
                    .clickable { onNavigateToRegister() },
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

