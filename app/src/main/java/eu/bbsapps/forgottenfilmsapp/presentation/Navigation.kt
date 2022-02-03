package eu.bbsapps.forgottenfilmsapp.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import eu.bbsapps.forgottenfilmsapp.presentation.auth.login.LoginScreen
import eu.bbsapps.forgottenfilmsapp.presentation.auth.register.RegisterScreen
import eu.bbsapps.forgottenfilmsapp.presentation.splash.SplashScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route
    ) {
        composable(
            route = Screen.SplashScreen.route
        ) {
            SplashScreen(navController)
        }

        composable(
            route = Screen.LoginScreen.route
        ) {
            LoginScreen(navController)
        }

        composable(
            route = Screen.RegisterScreen.route
        ) {
            RegisterScreen(navController)
        }

        composable(
            route = Screen.HomeScreen.route
        ) {
            //  HomeScreen(navController)
        }

        composable(
            route = Screen.SearchScreen.route
        ) {
            //  SearchScreen(navController)
        }

        composable(
            route = Screen.MoreScreen.route
        ) {
            // MoreScreen(navController)
        }

        composable(
            route = Screen.MoviesScreen.route
        ) {
            //  MoviesScreen(navController)
        }

        composable(
            route = Screen.MyListScreen.route
        ) {
            //  MyListScreen(navController)
        }

        composable(
            route = Screen.MovieDetailsScreen.route + "/{movieId}"
        ) {
            // MovieDetailsScreen(navController)
        }

        composable(
            route = Screen.MovieWatchScreen.route + "/{movieUrl}/{movieGenre}"
        ) {
            //  MovieWatchScreen(navController)
        }

        composable(
            route = Screen.AdminPanelScreen.route
        ) {
            // AdminPanelScreen(navController)
        }
    }
}