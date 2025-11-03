package views

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.midnightphonk.R
import components.GradientBackground
import viewmodels.RegisterViewModel
import kotlinx.coroutines.launch


@Composable
fun AppTextFieldColorsRegister() = OutlinedTextFieldDefaults.colors(
    focusedTextColor = Color.White,
    unfocusedTextColor = Color.White,
    cursorColor = Color.White,
    focusedBorderColor = Color.White,
    unfocusedBorderColor = Color.Gray,
    focusedLabelColor = Color.White,
    unfocusedLabelColor = Color.Gray
)

@Composable
fun AppButtonColorsRegister() = ButtonDefaults.buttonColors(
    containerColor = Color(0xFF6A0DAD),
    contentColor = Color.White,
    disabledContainerColor = Color.DarkGray,
    disabledContentColor = Color.Gray
)
@Composable
fun RegisterView(onNavigateToLogin: () -> Unit,
                 viewModel: RegisterViewModel = viewModel()
) {
    var showError by remember { mutableStateOf<String?>(null) }
    var showSuccess by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

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

            OutlinedTextField(
                value = viewModel.email,
                onValueChange = { viewModel.updateEmail(it) },
                label = { Text("Ingrese su correo") },
                modifier = Modifier.fillMaxWidth(),
                colors = AppTextFieldColorsRegister()
            )

            if (viewModel.emailError != null) {
                Text(
                    text = viewModel.emailError!!,
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
                colors = AppTextFieldColorsRegister()
            )

            if (viewModel.passwordError != null) {
                Text(
                    text = viewModel.passwordError!!,
                    color = Color.Red,
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = viewModel.confirmPassword,
                onValueChange = { viewModel.updateConfirmPassword(it) },
                label = { Text("Confirme su contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                isError = viewModel.confirmPasswordError != null,
                modifier = Modifier.fillMaxWidth(),
                colors = AppTextFieldColorsRegister(),
                textStyle = TextStyle(color = Color.White)
            )

            if (viewModel.confirmPasswordError != null) {
                Text(
                    text = viewModel.confirmPasswordError!!,
                    color = Color.Red,
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    coroutineScope.launch {
                        val result = viewModel.registerUser()
                        when (result) {
                            is viewmodels.RegistrationResult.Success -> {
                                showSuccess = true
                                showError = null
                            }
                            is viewmodels.RegistrationResult.Error -> {
                                showError = result.message
                                showSuccess = false
                            }
                        }
                    }
                },
                enabled = viewModel.isValid(),
                modifier = Modifier.fillMaxWidth(),
                colors = AppButtonColorsRegister()
            ) {
                Text("Crear cuenta")
            }

            if (showError != null) {
                Text(
                    text = showError!!,
                    color = Color.Red,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            if (showSuccess) {
                Text(
                    text = "¡Cuenta creada exitosamente!",
                    color = Color.Green,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "¿Ya tienes una cuenta? Inicia sesión",
                modifier = Modifier.clickable { onNavigateToLogin() },
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
        }
    }
}