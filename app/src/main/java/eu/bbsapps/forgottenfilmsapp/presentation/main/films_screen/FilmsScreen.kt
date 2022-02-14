package eu.bbsapps.forgottenfilmsapp.presentation.main.films_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import eu.bbsapps.forgottenfilmsapp.presentation.Screen
import eu.bbsapps.forgottenfilmsapp.presentation.main.components.BottomBar
import eu.bbsapps.forgottenfilmsapp.presentation.main.components.BottomBarEntry
import eu.bbsapps.forgottenfilmsapp.presentation.main.components.MainTopBar
import eu.bbsapps.forgottenfilmsapp.presentation.main.home_screen.components.FilmFeedRow
import eu.bbsapps.forgottenfilmsapp.presentation.ui.theme.hugeSpacerValue
import kotlinx.coroutines.launch

@Composable
fun FilmsScreen(navController: NavController, viewModel: FilmsViewModel = hiltViewModel()) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        Modifier.fillMaxSize(),
        backgroundColor = MaterialTheme.colors.background,
        topBar = {
            MainTopBar(
                onMoviesClicked = {
                },
                navController = navController
            )
        },
        bottomBar = {
            BottomBar(
                currentlySelected = BottomBarEntry.NONE,
                navController = navController
            )
        },
        scaffoldState = scaffoldState
    ) {
        val state = viewModel.state.value
        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        if (state.error.isNotBlank()) {
            LaunchedEffect(key1 = state.error) {
                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(state.error)
                }
            }
        }

        state.films?.let {
            LazyColumn {
                items(state.films) {
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
}