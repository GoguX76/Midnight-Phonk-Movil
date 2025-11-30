
package views

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import components.AppBottomBar
import navigation.AppScreens
import navigation.MainScreenRoutes
import viewmodels.CartViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenView(
    navController: NavController,
    onNavigateToAddProduct: () -> Unit,
    onNavigateToProductDetail: (Int) -> Unit,
    onNavigateToPurchaseResult: () -> Unit,
    cartViewModel: CartViewModel
) {
    val mainNavController = rememberNavController()

    val navBackStackEntry by mainNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: MainScreenRoutes.HOME

    Scaffold(
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
                    onNavigateToProductDetail = onNavigateToProductDetail,
                    cartViewModel = cartViewModel
                )
            }
            composable(MainScreenRoutes.CART) {
                CartView(
                    paddingValues = paddingValues,
                    cartViewModel = cartViewModel,
                    onNavigateToResult = onNavigateToPurchaseResult
                )
            }
            composable(MainScreenRoutes.ACCOUNT) {
                AccountView(navController = navController)
            }
        }
    }
}
