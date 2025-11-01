
package navigation

// Rutas principales de la app (el flujo general)
sealed class AppScreens(val route: String) {
    object Login : AppScreens("login")
    object Register : AppScreens("register")
    object Main : AppScreens("main")
}

// Rutas INTERNAS de la pantalla principal (para la barra de navegación)
object MainScreenRoutes {
    const val HOME = "home"
    const val ABOUT_US = "about_us"
    const val CATALOG = "catalog"
}
