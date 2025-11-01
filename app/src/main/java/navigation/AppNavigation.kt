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
import views.HomeView
import views.LoginView
import views.RegisterView

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = AppScreens.Login.route ) {
        composable(AppScreens.Register.route) {
            RegisterView(
                onNavigateToLogin = {
                    navController.navigate(AppScreens.Login.route)
                }
            )
        }
        composable(AppScreens.Login.route) {
            LoginView(
                onNavigateToRegister = {
                    navController.navigate("register") {
                        popUpTo(AppScreens.Login.route) {inclusive = true}
                    }
                },
                onNavigateToHome = {
                    navController.navigate("home") {
                        popUpTo(AppScreens.Home.route) {inclusive = true}
                    }
                }
            )
        }
        composable(AppScreens.Home.route) {
            HomeView()
        }
    }
}