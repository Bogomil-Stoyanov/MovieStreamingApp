package eu.bbsapps.forgottenfilmsapp.presentation.main.search_screen

import android.content.pm.ActivityInfo
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import eu.bbsapps.forgottenfilmsapp.R
import eu.bbsapps.forgottenfilmsapp.common.Constants
import eu.bbsapps.forgottenfilmsapp.presentation.LockScreenOrientation
import eu.bbsapps.forgottenfilmsapp.presentation.Screen
import eu.bbsapps.forgottenfilmsapp.presentation.components.FilmListItem
import eu.bbsapps.forgottenfilmsapp.presentation.main.components.BottomBar
import eu.bbsapps.forgottenfilmsapp.presentation.main.components.BottomBarEntry
import eu.bbsapps.forgottenfilmsapp.presentation.main.components.MainTopBar
import eu.bbsapps.forgottenfilmsapp.presentation.main.search_screen.component.OutlinedSearchField
import eu.bbsapps.forgottenfilmsapp.presentation.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(navController: NavController, viewModel: SearchViewModel = hiltViewModel()) {

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

    Scaffold(
        Modifier.fillMaxSize(),
        backgroundColor = MaterialTheme.colors.background,
        topBar = {
            MainTopBar(
                navController = navController
            )
        },
        bottomBar = {
            BottomBar(
                currentlySelected = BottomBarEntry.SEARCH,
                navController = navController,
                onSearchClicked = {}
            )
        },
        scaffoldState = scaffoldState
    ) {

        val state = viewModel.state.value

        BoxWithConstraints {
            val width = maxWidth
            val fontSize =
                if (width < Constants.BIG_SCREEN_THRESHOLD) smallFontValue else mediumBigFontValue

            Column(Modifier.fillMaxSize()) {

                OutlinedSearchField(
                    text = viewModel.query.value,
                    onValueChange = { viewModel.onEvent(SearchEvent.EnteredQuery(it)) },
                    onSearchClicked = {
                        viewModel.onEvent(SearchEvent.SearchClicked)
                    },
                    textStyle = TextStyle(fontSize = fontSize)
                )

                if (state.films.isNotEmpty()) {
                    LazyColumn(Modifier.fillMaxSize()) {
                        item {
                            Row {
                                Spacer(modifier = Modifier.width(smallSpacerValue))
                                Text(
                                    text = stringResource(R.string.results),
                                    color = MaterialTheme.colors.onSurface,
                                    fontSize = if (width < Constants.BIG_SCREEN_THRESHOLD)
                                        smallMediumFontValue else mediumFontValue
                                )
                            }
                        }
                        items(state.films) {
                            FilmListItem(item = it, onMovieClicked = {
                                navController.navigate(Screen.FilmDetailsScreen.route + "/${it.id}")
                            })
                        }
                        item {
                            Spacer(modifier = Modifier.height(hugeSpacerValue))
                        }
                    }
                } else if (state.films.isEmpty() && !state.isLoading && state.error.isBlank() && state.hasSearched) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = stringResource(R.string.no_films_found),
                            color = MaterialTheme.colors.onSurface,
                            fontSize = fontSize
                        )
                    }
                } else if (state.films.isEmpty() && !state.isLoading && state.error.isBlank() && !state.hasSearched) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = stringResource(R.string.search_film_by_title),
                            color = MaterialTheme.colors.onSurface,
                            fontSize = fontSize
                        )
                    }
                }

                if (state.isLoading) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(mediumSpacerValue))
                        CircularProgressIndicator(color = MaterialTheme.colors.primary)
                    }

                }
            }
        }

        val context = LocalContext.current
        if (state.error.isNotBlank()) {
            LaunchedEffect(key1 = state.error) {
                scope.launch {
                    val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
                        message = state.error,
                        actionLabel = context.getString(R.string.retry),
                        duration = SnackbarDuration.Indefinite,
                    )
                    if (snackbarResult == SnackbarResult.ActionPerformed) {
                        viewModel.onEvent(SearchEvent.SearchClicked)
                    }
                }
            }
        }
    }
}
