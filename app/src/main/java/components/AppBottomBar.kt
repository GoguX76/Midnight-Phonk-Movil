
package components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import navigation.MainScreenRoutes

@Composable
fun AppBottomBar(
    currentRoute: String,
    onNavigate: (String) -> Unit
) {
    val items = listOf(
        MainScreenRoutes.HOME to "Inicio",
        MainScreenRoutes.ABOUT_US to "Nosotros",
        MainScreenRoutes.CATALOG to "Catálogo",
        MainScreenRoutes.CART to "Carrito"
    )

    val gradientColors = listOf(
        Color(0xFF1f2937),
        Color(0xFF4f46e5),
        Color(0xFF9333ea)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(brush = Brush.verticalGradient(colors = gradientColors))
            .height(80.dp), // Typical height for a bottom app bar
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEach { (route, label) ->
            NavigationBarItem(
                modifier = Modifier.weight(1f),
                icon = { },
                label = { Text(label, color = Color.White) },
                selected = currentRoute == route,
                onClick = { onNavigate(route) }
            )
        }
    }
}
