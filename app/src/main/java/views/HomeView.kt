package views

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.midnightphonk.R
import components.GradientBackgroundHome
import kotlinx.coroutines.delay

@Composable
fun HomeView(paddingValues: PaddingValues) {
    // Animación 1: Logo con escala y bounce
    var logoVisible by remember { mutableStateOf(false) }
    val logoScale by animateFloatAsState(
        targetValue = if (logoVisible) 1f else 0.3f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "logoScale"
    )

    LaunchedEffect(Unit) {
        delay(200)
        logoVisible = true
    }

    GradientBackgroundHome {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo y Título Principal
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(16.dp))

                    Image(
                        painter = painterResource(id = R.drawable.midnight_phonk_logo),
                        contentDescription = "Midnight Phonk Logo",
                        modifier = Modifier
                            .size(150.dp)
                            .scale(logoScale)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    AnimatedVisibility(
                        visible = logoVisible,
                        enter = fadeIn(animationSpec = tween(800)) +
                                slideInVertically(
                                    initialOffsetY = { it / 2 },
                                    animationSpec = tween(800)
                                )
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "MIDNIGHT PHONK",
                                style = MaterialTheme.typography.displayMedium,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "Tu tienda de música Phonk",
                                style = MaterialTheme.typography.titleLarge,
                                color = Color.White.copy(alpha = 0.8f),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }

            // Mensaje de Bienvenida con Animación 2
            item {
                var welcomeVisible by remember { mutableStateOf(false) }

                LaunchedEffect(Unit) {
                    delay(600)
                    welcomeVisible = true
                }

                AnimatedVisibility(
                    visible = welcomeVisible,
                    enter = fadeIn(animationSpec = tween(800)) +
                            slideInHorizontally(
                                initialOffsetX = { -it / 2 },
                                animationSpec = tween(800)
                            )
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White.copy(alpha = 0.1f)
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "¡Bienvenido!",
                                style = MaterialTheme.typography.headlineMedium,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Descubre los mejores samples, kits y plugins para crear tu música Phonk perfecta",
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.White.copy(alpha = 0.9f),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }

            // Características Principales
            item {
                Text(
                    text = "¿Por qué elegirnos?",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Feature Cards con Animación 3 (stagger effect)
            item {
                var feature1Visible by remember { mutableStateOf(false) }
                var feature2Visible by remember { mutableStateOf(false) }
                var feature3Visible by remember { mutableStateOf(false) }

                LaunchedEffect(Unit) {
                    delay(1000)
                    feature1Visible = true
                    delay(200)
                    feature2Visible = true
                    delay(200)
                    feature3Visible = true
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    AnimatedVisibility(
                        visible = feature1Visible,
                        enter = fadeIn(animationSpec = tween(500)) +
                                slideInHorizontally(
                                    initialOffsetX = { it },
                                    animationSpec = tween(500)
                                )
                    ) {
                        FeatureCard(
                            icon = Icons.Default.Star,
                            title = "Calidad Premium",
                            description = "Todos nuestros productos son de alta calidad, perfectos para productores profesionales y principiantes"
                        )
                    }

                    AnimatedVisibility(
                        visible = feature2Visible,
                        enter = fadeIn(animationSpec = tween(500)) +
                                slideInHorizontally(
                                    initialOffsetX = { it },
                                    animationSpec = tween(500)
                                )
                    ) {
                        FeatureCard(
                            icon = Icons.Default.ShoppingCart,
                            title = "Fácil de Usar",
                            description = "Navega, compra y descarga tus samples favoritos en segundos"
                        )
                    }

                    AnimatedVisibility(
                        visible = feature3Visible,
                        enter = fadeIn(animationSpec = tween(500)) +
                                slideInHorizontally(
                                    initialOffsetX = { it },
                                    animationSpec = tween(500)
                                )
                    ) {
                        FeatureCard(
                            icon = Icons.Default.Favorite,
                            title = "Comunidad Phonk",
                            description = "Únete a nuestra comunidad de productores apasionados por el género Phonk"
                        )
                    }
                }
            }

            // Categorías Destacadas
            item {
                Text(
                    text = "Categorías Populares",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                var categoriesVisible by remember { mutableStateOf(false) }

                LaunchedEffect(Unit) {
                    delay(1600)
                    categoriesVisible = true
                }

                // Animación de aparición con offset vertical diferente para cada fila
                AnimatedVisibility(
                    visible = categoriesVisible,
                    enter = fadeIn(tween(600)) + slideInVertically(
                        initialOffsetY = { it / 3 },
                        animationSpec = tween(600)
                    )
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        CategoryCard(
                            title = "Sample Packs",
                            emoji = "🎵",
                            modifier = Modifier.weight(1f)
                        )
                        CategoryCard(
                            title = "Sound Kits",
                            emoji = "🎹",
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            item {
                var categoriesVisible2 by remember { mutableStateOf(false) }

                LaunchedEffect(Unit) {
                    delay(1800)
                    categoriesVisible2 = true
                }

                AnimatedVisibility(
                    visible = categoriesVisible2,
                    enter = fadeIn(tween(600)) + slideInVertically(
                        initialOffsetY = { it / 3 },
                        animationSpec = tween(600)
                    )
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        CategoryCard(
                            title = "Plugins",
                            emoji = "🎛️",
                            modifier = Modifier.weight(1f)
                        )
                        CategoryCard(
                            title = "Todos",
                            emoji = "🔥",
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            // Llamada a la Acción con Animación infinita del emoji
            item {
                // Animación infinita de pulsación
                val infiniteTransition = rememberInfiniteTransition(label = "emoji_pulse")
                val emojiScale by infiniteTransition.animateFloat(
                    initialValue = 1f,
                    targetValue = 1.2f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(1000, easing = EaseInOutCubic),
                        repeatMode = RepeatMode.Reverse
                    ),
                    label = "emojiScale"
                )

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF6A0DAD).copy(alpha = 0.9f)
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "🎧",
                            style = MaterialTheme.typography.displayLarge,
                            modifier = Modifier.scale(emojiScale)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "¿Listo para crear?",
                            style = MaterialTheme.typography.headlineMedium,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Explora nuestro catálogo completo y encuentra los sonidos perfectos para tu próximo track",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White.copy(alpha = 0.9f),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            // Espaciado final
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun FeatureCard(
    icon: ImageVector,
    title: String,
    description: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.15f)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(48.dp),
                color = Color(0xFF6A0DAD).copy(alpha = 0.3f),
                shape = RoundedCornerShape(12.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }
        }
    }
}

@Composable
fun CategoryCard(
    title: String,
    emoji: String,
    modifier: Modifier = Modifier
) {
    // Animación de flotación del emoji
    val infiniteTransition = rememberInfiniteTransition(label = "categoryFloat")
    val emojiOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -8f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "emojiFloat"
    )

    Card(
        modifier = modifier.height(120.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.12f)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = emoji,
                style = MaterialTheme.typography.displayMedium,
                modifier = Modifier.graphicsLayer {
                    translationY = emojiOffset
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

