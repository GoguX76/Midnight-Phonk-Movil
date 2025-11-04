package views

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.midnightphonk.R
import components.GradientBackgroundHome
import kotlinx.coroutines.delay

@Composable
fun AboutUsView(paddingValues: PaddingValues) {
    GradientBackgroundHome {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            item {
                // Animación de shimmer/brillo en el logo
                val infiniteTransition = rememberInfiniteTransition(label = "shimmer")
                val shimmerAlpha by infiniteTransition.animateFloat(
                    initialValue = 0.3f,
                    targetValue = 0.8f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(1500, easing = LinearEasing),
                        repeatMode = RepeatMode.Reverse
                    ),
                    label = "shimmerAlpha"
                )

                Box(
                    modifier = Modifier
                        .height(150.dp)
                        .fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.midnight_phonk_logo),
                        contentDescription = "Logo de Midnight Phonk",
                        modifier = Modifier
                            .height(150.dp)
                            .fillMaxWidth(),
                        alpha = shimmerAlpha
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))

                var titleVisible by remember { mutableStateOf(false) }
                var textVisible by remember { mutableStateOf(false) }

                LaunchedEffect(Unit) {
                    delay(300)
                    titleVisible = true
                    delay(400)
                    textVisible = true
                }

                AnimatedVisibility(
                    visible = titleVisible,
                    enter = fadeIn(tween(600)) + expandVertically()
                ) {
                    Text(
                        text = "Sobre Nosotros",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                AnimatedVisibility(
                    visible = textVisible,
                    enter = fadeIn(tween(800))
                ) {
                    Text(
                        text = """Midnight Phonk nació de la pasión por la música y el deseo de compartir la cultura Phonk con el mundo. Inspirados por los sonidos underground y la energía vibrante de la escena brasileña, decidimos crear un espacio único para productores, oyentes y amantes del género.

Nos dedicamos a ofrecer recursos de calidad: samples, kits, catálogos musicales, artículos y tutoriales, todo pensado para impulsar la creatividad y el crecimiento de la comunidad Phonk. Nuestro objetivo es conectar, inspirar y acompañar a quienes buscan innovar y dejar huella en la música.

¿Por qué lo hacemos? Creemos en el poder de la música para unir personas y culturas. Queremos que más artistas descubran el Phonk y encuentren aquí las herramientas para crear su propio sonido.

Nuestra pasión: Cada proyecto, cada sample y cada historia que compartimos está impregnada de nuestro amor por el género y su cultura. Innovar, colaborar y crecer junto a la comunidad es lo que nos impulsa día a día.

Gracias por ser parte de Midnight Phonk. ¡La historia la escribimos juntos!""",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White,
                        textAlign = TextAlign.Justify
                    )
                }
                Spacer(modifier = Modifier.height(32.dp))
            }

            item {
                Text(
                    text = "Nuestro Equipo",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                TeamMember(name = "Sebastián Alvarez", role = "Desarrollador")
                Spacer(modifier = Modifier.height(8.dp))
                TeamMember(name = "Sebastián Godoy", role = "Desarrollador")
                Spacer(modifier = Modifier.height(32.dp))
            }

            item {
                Text(
                    text = "Contacto",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "info@midnightphonk.com",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun TeamMember(name: String, role: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = name, style = MaterialTheme.typography.titleMedium, color = Color.White)
        Text(text = role, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
    }
}

