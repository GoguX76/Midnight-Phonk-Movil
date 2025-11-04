package views

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import components.GradientBackgroundHome

@Composable
fun ProfileView(paddingValues: PaddingValues) {
    GradientBackgroundHome {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Vista de Perfil",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White
            )
        }
    }
}

