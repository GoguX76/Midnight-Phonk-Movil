
package navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import views.*
import viewmodels.CartViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val cartViewModel: CartViewModel = viewModel()

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
                onNavigateToAddProduct = { navController.navigate(AppScreens.AddProduct.route) },
                onNavigateToProductDetail = { navController.navigate("${AppScreens.ProductDetail.route}/$it") },
                onNavigateToPurchaseResult = { navController.navigate(AppScreens.PurchaseResult.route) },
                cartViewModel = cartViewModel
            )
        }

        composable(AppScreens.AddProduct.route) {
            AddProductView(
                onProductAdded = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }

        composable(
            route = "${AppScreens.ProductDetail.route}/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId") ?: 0
            ProductDetailView(
                productId = productId,
                onNavigateUp = { navController.navigateUp() },
                onProductDeleted = { navController.popBackStack() },
                onNavigateToEditProduct = { navController.navigate("${AppScreens.EditProduct.route}/$it") },
                cartViewModel = cartViewModel
            )
        }

        composable(
            route = "${AppScreens.EditProduct.route}/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId") ?: 0
            EditProductView(
                productId = productId,
                onProductUpdated = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }

        composable(AppScreens.PurchaseResult.route) {
            val message = cartViewModel.purchaseResult ?: "Ocurrió un error inesperado."
            PurchaseResultView(
                message = message,
                onNavigateToCatalog = {
                    navController.navigate(AppScreens.Main.route) {
                        // Limpia la pila de navegación hasta la pantalla principal
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                    }
                    cartViewModel.clearPurchaseResult() // Limpia el mensaje después de navegar
                }
            )
        }
    }
}
