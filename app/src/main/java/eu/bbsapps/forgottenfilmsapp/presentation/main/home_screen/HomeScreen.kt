package eu.bbsapps.forgottenfilmsapp.presentation.main.home_screen

import android.content.pm.ActivityInfo
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import eu.bbsapps.forgottenfilmsapp.R
import eu.bbsapps.forgottenfilmsapp.presentation.LockScreenOrientation
import eu.bbsapps.forgottenfilmsapp.presentation.Screen
import eu.bbsapps.forgottenfilmsapp.presentation.main.components.BottomBar
import eu.bbsapps.forgottenfilmsapp.presentation.main.components.BottomBarEntry
import eu.bbsapps.forgottenfilmsapp.presentation.main.components.MainTopBar
import eu.bbsapps.forgottenfilmsapp.presentation.main.home_screen.components.FilmFeedRow
import eu.bbsapps.forgottenfilmsapp.presentation.ui.theme.hugeSpacerValue
import kotlinx.coroutines.launch


@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = hiltViewModel()) {

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

    Scaffold(
        Modifier.fillMaxSize(),
        backgroundColor = MaterialTheme.colors.background,
        topBar = {
            MainTopBar(
                onMoviesClicked = {
                    navController.navigate(Screen.MoviesScreen.route)
                },
                onMyListClicked = {
                    navController.navigate(Screen.MyListScreen.route)
                },
                navController = navController,
                onLogoClicked = {})
        },
        bottomBar = {
            BottomBar(
                currentlySelected = BottomBarEntry.HOME,
                onHomeClicked = {},
                onSearchClicked = {
                    navController.navigate(Screen.SearchScreen.route)
                },
                onMoreClicked = {
                    navController.navigate(Screen.MoreScreen.route)
                },
                navController = navController
            )
        },
        scaffoldState = scaffoldState
    ) {

        val state = viewModel.state.value

        SwipeRefresh(
            modifier = Modifier.fillMaxSize(),
            state = rememberSwipeRefreshState(isRefreshing = state.isLoading),
            onRefresh = { viewModel.getFeed() }) {
            state.movie?.let {
                LazyColumn {
                    items(it) {
                        FilmFeedRow(title = it.title, filmList = it.films) { id ->
                            navController.navigate(Screen.FilmDetailsScreen.route + "/${id}")
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.height(hugeSpacerValue))
                    }
                }
            }
        }

        if (state.error.isNotBlank()) {
            val context = LocalContext.current
            LaunchedEffect(key1 = state.error) {
                scope.launch {
                    val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
                        message = state.error,
                        actionLabel = context.getString(R.string.retry),
                        duration = SnackbarDuration.Indefinite,
                    )

                    when (snackbarResult) {
                        SnackbarResult.ActionPerformed -> {
                            viewModel.getFeed()
                        }
                        else -> {
                        }
                    }
                }
            }
        }

    }
}