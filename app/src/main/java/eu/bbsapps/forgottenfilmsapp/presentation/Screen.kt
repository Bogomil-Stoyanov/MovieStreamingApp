package eu.bbsapps.forgottenfilmsapp.presentation

sealed class Screen(val route: String) {
    object SplashScreen : Screen("splash_screen")
    object LoginScreen : Screen("login_screen")
    object RegisterScreen : Screen("register_screen")
    object HomeScreen : Screen("home_screen")
    object SearchScreen : Screen("search_screen")
    object MoreScreen : Screen("more_screen")
    object MoviesScreen : Screen("films_screen")
    object MyListScreen : Screen("my_list_screen")
    object MovieDetailsScreen : Screen("film_details_screen")
    object MovieWatchScreen : Screen("film_watch_screen")
    object AdminPanelScreen : Screen("admin_panel_screen")

}