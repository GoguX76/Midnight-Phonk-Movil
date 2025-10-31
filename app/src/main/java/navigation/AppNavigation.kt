package navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import views.LoginView
import views.RegisterView

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "register" ) {
        composable("register") {
            RegisterView {
                navController.navigate("login")
            }
        }
        composable("login") {
            LoginView(
                onNavigateToRegister = {
                    navController.navigate("register") {
                        popUpTo("login") {inclusive = true}
                    }
                }
            )
        }
    }
}