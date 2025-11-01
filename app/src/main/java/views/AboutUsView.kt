
package views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.midnightphonk.R
import components.GradientBackgroundHome

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
                Image(
                    painter = painterResource(id = R.drawable.midnight_phonk_logo),
                    contentDescription = "Logo de Midnight Phonk",
                    modifier = Modifier
                        .height(150.dp)
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Sobre Nosotros",
                    fontSize = 28.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = """Midnight Phonk nació de la pasión por la música y el deseo de compartir la cultura Phonk con el mundo. Inspirados por los sonidos underground y la energía vibrante de la escena brasileña, decidimos crear un espacio único para productores, oyentes y amantes del género.

Nos dedicamos a ofrecer recursos de calidad: samples, kits, catálogos musicales, artículos y tutoriales, todo pensado para impulsar la creatividad y el crecimiento de la comunidad Phonk. Nuestro objetivo es conectar, inspirar y acompañar a quienes buscan innovar y dejar huella en la música.

¿Por qué lo hacemos? Creemos en el poder de la música para unir personas y culturas. Queremos que más artistas descubran el Phonk y encuentren aquí las herramientas para crear su propio sonido.

Nuestra pasión: Cada proyecto, cada sample y cada historia que compartimos está impregnada de nuestro amor por el género y su cultura. Innovar, colaborar y crecer junto a la comunidad es lo que nos impulsa día a día.

Gracias por ser parte de Midnight Phonk. ¡La historia la escribimos juntos!""",
                    fontSize = 16.sp,
                    color = Color.White,
                    textAlign = TextAlign.Justify
                )
                Spacer(modifier = Modifier.height(32.dp))
            }

            item {
                Text(
                    text = "Nuestro Equipo",
                    fontSize = 24.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
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
                    fontSize = 24.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "info@midnightphonk.com",
                    fontSize = 16.sp,
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
        Text(text = name, fontSize = 18.sp, color = Color.White, fontWeight = FontWeight.SemiBold)
        Text(text = role, fontSize = 14.sp, color = Color.Gray)
    }
}
