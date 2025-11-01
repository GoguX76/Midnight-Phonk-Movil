package views

import com.example.midnightphonk.R
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import viewmodels.LoginViewModel
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.clickable
import androidx.compose.ui.composed
import androidx.compose.ui.text.font.FontWeight
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
fun LoginView(onNavigateToRegister: () -> Unit,
              onNavigateToHome: () -> Unit,
              viewModel: LoginViewModel = viewModel()
) {
    GradientBackground{
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
                    .size(120.dp)
                    .padding(bottom = 24.dp)
            )

            OutlinedTextField(
                value = viewModel.email,
                onValueChange = {viewModel.updateEmail(it) },
                label = {Text("Ingrese su correo")},
                modifier = Modifier.fillMaxWidth(),
                colors = AppTextFieldColorsLogin()
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
                onValueChange = {viewModel.updatePassword(it)},
                label = {Text("Ingrese su contraseña")},
                visualTransformation = PasswordVisualTransformation(),
                isError = viewModel.passwordError != null,
                modifier = Modifier.fillMaxWidth(),
                colors = AppTextFieldColorsLogin()
            )

            if (viewModel.passwordError != null) {
                Text(
                    text = viewModel.passwordError!!,
                    color = Color.Red,
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {onNavigateToHome()},
                enabled = viewModel.isValid(),
                modifier = Modifier
                    .fillMaxWidth(),
                colors = AppButtonColorsLogin()
            ) {
                Text("Iniciar sesión")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "¿No tienes una cuenta? Crea una aquí",
                modifier = Modifier
                    .padding(16.dp)
                    .clickable { onNavigateToRegister() },
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
        }
    }
}