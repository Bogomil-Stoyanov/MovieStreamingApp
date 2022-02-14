package eu.bbsapps.forgottenfilmsapp.presentation.main.my_list_screen

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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import eu.bbsapps.forgottenfilmsapp.R
import eu.bbsapps.forgottenfilmsapp.common.Constants.BIG_SCREEN_THRESHOLD
import eu.bbsapps.forgottenfilmsapp.presentation.LockScreenOrientation
import eu.bbsapps.forgottenfilmsapp.presentation.Screen
import eu.bbsapps.forgottenfilmsapp.presentation.components.FilmListItem
import eu.bbsapps.forgottenfilmsapp.presentation.main.components.BottomBar
import eu.bbsapps.forgottenfilmsapp.presentation.main.components.BottomBarEntry
import eu.bbsapps.forgottenfilmsapp.presentation.main.components.MainTopBar
import eu.bbsapps.forgottenfilmsapp.presentation.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun MyListScreen(navController: NavController, viewModel: MyListViewModel = hiltViewModel()) {

    LaunchedEffect(key1 = true) {
        viewModel.getMoviesList()
        viewModel.getNickname()
    }

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

    Scaffold(
        Modifier.fillMaxSize(),
        backgroundColor = MaterialTheme.colors.background,
        topBar = {
            MainTopBar(
                onMyListClicked = {
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

        BoxWithConstraints(Modifier.fillMaxSize()) {
            val width = maxWidth
            val fontSize =
                if (width < BIG_SCREEN_THRESHOLD) smallMediumFontValue else mediumBigFontValue
            if (state.filmList.isNotEmpty()) {
                LazyColumn(Modifier.fillMaxSize()) {
                    item {
                        Row {
                            Spacer(modifier = Modifier.width(smallSpacerValue))
                            if (viewModel.nickname.value.isNotBlank())
                                Text(
                                    text = stringResource(
                                        id = R.string.list,
                                        viewModel.nickname.value
                                    ),
                                    color = MaterialTheme.colors.onSurface,
                                    fontSize = fontSize
                                )
                        }
                    }
                    items(state.filmList) {
                        FilmListItem(item = it, onMovieClicked = {
                            navController.navigate(Screen.FilmDetailsScreen.route + "/${it.id}")
                        })
                    }

                    item {
                        Spacer(modifier = Modifier.height(hugeSpacerValue))
                    }
                }
            } else {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = stringResource(R.string.no_films_in_list),
                        fontSize = fontSize,
                        color = MaterialTheme.colors.onSurface
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
                        viewModel.getMoviesList()
                        viewModel.getNickname()
                    }
                }
            }
        }
    }
}