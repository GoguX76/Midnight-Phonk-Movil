
package views

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import components.AppBottomBar
import navigation.MainScreenRoutes
import viewmodels.CartViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenView(onNavigateToAddProduct: () -> Unit) {
    val mainNavController = rememberNavController()
    val cartViewModel: CartViewModel = viewModel()

    val navBackStackEntry by mainNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: MainScreenRoutes.HOME

    val currentTitle = when (currentRoute) {
        MainScreenRoutes.HOME -> "Inicio"
        MainScreenRoutes.ABOUT_US -> "Quiénes somos"
        MainScreenRoutes.CATALOG -> "Catálogo"
        MainScreenRoutes.CART -> "Carrito"
        else -> "Midnight Phonk"
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(currentTitle) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF6A0DAD),
                    titleContentColor = Color.White
                )
            )
        },
        bottomBar = {
            AppBottomBar(
                currentRoute = currentRoute,
                onNavigate = { route: String ->
                    mainNavController.navigate(route) {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = mainNavController,
            startDestination = MainScreenRoutes.HOME,
            modifier = Modifier.fillMaxSize()
        ) {
            composable(MainScreenRoutes.HOME) {
                HomeView(paddingValues = paddingValues)
            }
            composable(MainScreenRoutes.ABOUT_US) {
                AboutUsView(paddingValues = paddingValues)
            }
            composable(MainScreenRoutes.CATALOG) {
                CatalogView(
                    paddingValues = paddingValues,
                    onNavigateToAddProduct = onNavigateToAddProduct,
                    cartViewModel = cartViewModel
                )
            }
            composable(MainScreenRoutes.CART) {
                CartView(
                    paddingValues = paddingValues,
                    cartViewModel = cartViewModel
                )
            }
        }
    }
}
