
package navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import views.AddProductView
import views.LoginView
import views.RegisterView
import views.MainScreenView

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = AppScreens.Login.route) {

        composable(AppScreens.Register.route) {
            RegisterView(
                onNavigateToLogin = {

                    navController.navigate(AppScreens.Login.route) {
                        popUpTo(AppScreens.Register.route) { inclusive = true }
                    }
                }
            )
        }

        composable(AppScreens.Login.route) {
            LoginView(
                onNavigateToRegister = {

                    navController.navigate(AppScreens.Register.route)
                },
                onNavigateToHome = {

                    navController.navigate(AppScreens.Main.route) {

                        popUpTo(AppScreens.Login.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(AppScreens.Main.route) {
            MainScreenView(
                onNavigateToAddProduct = {
                    navController.navigate(AppScreens.AddProduct.route)
                }
            )
        }

        composable(AppScreens.AddProduct.route) {
            AddProductView(
                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}
