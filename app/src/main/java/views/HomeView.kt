
package views

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import components.GradientBackgroundHome

@Composable
fun HomeView(paddingValues: PaddingValues) {
    GradientBackgroundHome {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Bienvenido a Midnight Phonk",
                style = MaterialTheme.typography.displayMedium,
                color = Color.White
            )
        }
    }
}
