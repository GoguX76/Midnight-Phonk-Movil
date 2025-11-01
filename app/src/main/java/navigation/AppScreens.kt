package navigation

sealed class AppScreens(val route: String) {
    object Register : AppScreens("register")
    object Login: AppScreens("login")
    object Home: AppScreens("home")
}