package views

import com.example.midnightphonk.R
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import viewmodels.RegisterViewModel
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.clickable
import androidx.compose.ui.text.font.FontWeight

@Composable
fun RegisterView(onNavigateToLogin: () -> Unit) {
    val viewModel = remember { RegisterViewModel()}

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
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
            modifier = Modifier.fillMaxWidth()
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
            modifier = Modifier.fillMaxWidth()
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
            onValueChange = {viewModel.confirmPassword(it)},
            label = {Text("Confirme su contraseña")},
            visualTransformation = PasswordVisualTransformation(),
            isError = viewModel.confirmPasswordError != null,
            modifier = Modifier.fillMaxWidth()
        )

        if (viewModel.confirmPasswordError != null) {
            Text(
                text = viewModel.confirmPasswordError!!,
                color = Color.Red,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }

        Button(
            onClick = {},
            enabled = viewModel.isValid(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Crear cuenta")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "¿Ya tienes una cuenta? Inicia sesión",
            modifier = Modifier.clickable {onNavigateToLogin()},
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )
    }
}