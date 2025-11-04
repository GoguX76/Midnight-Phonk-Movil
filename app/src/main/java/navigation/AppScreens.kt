
package navigation

// Rutas principales de la app (el flujo general)
sealed class AppScreens(val route: String) {
    object Login : AppScreens("login")
    object Register : AppScreens("register")
    object Main : AppScreens("main")
    object AddProduct : AppScreens("add_product")
    object ProductDetail : AppScreens("product_detail")
    object EditProduct : AppScreens("edit_product") // Nueva ruta
    object PurchaseResult : AppScreens("purchase_result")
}

// Rutas INTERNAS de la pantalla principal (para la barra de navegación)
object MainScreenRoutes {
    const val HOME = "home"
    const val ABOUT_US = "about_us"
    const val CATALOG = "catalog"
    const val CART = "cart"
}
