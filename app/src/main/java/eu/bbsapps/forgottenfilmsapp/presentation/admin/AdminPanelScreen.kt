package eu.bbsapps.forgottenfilmsapp.presentation.admin

import android.content.pm.ActivityInfo
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import eu.bbsapps.forgottenfilmsapp.R
import eu.bbsapps.forgottenfilmsapp.common.Constants
import eu.bbsapps.forgottenfilmsapp.data.remote.dto.responses.FilmFeedItem
import eu.bbsapps.forgottenfilmsapp.presentation.LockScreenOrientation
import eu.bbsapps.forgottenfilmsapp.presentation.admin.components.AdminPanelTopBar
import eu.bbsapps.forgottenfilmsapp.presentation.admin.components.StatisticsItem
import eu.bbsapps.forgottenfilmsapp.presentation.admin.dialogs.FilmsListDialog
import eu.bbsapps.forgottenfilmsapp.presentation.admin.dialogs.UsersListDialog
import eu.bbsapps.forgottenfilmsapp.presentation.components.stats.Statistics
import eu.bbsapps.forgottenfilmsapp.presentation.components.stats.getFormattedTimeFromSeconds
import eu.bbsapps.forgottenfilmsapp.presentation.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun AdminPanelScreen(navController: NavController, viewModel: AdminViewModel = hiltViewModel()) {
    LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    Scaffold(backgroundColor = MaterialTheme.colors.background, topBar = {
        AdminPanelTopBar {
            focusManager.clearFocus()
            navController.navigateUp()
        }
    }, scaffoldState = scaffoldState) {
        BoxWithConstraints {
            val width = maxWidth

            val smallFontSize =
                if (width < Constants.BIG_SCREEN_THRESHOLD) mediumFontValue else bigFontValue
            val largeFontSize =
                if (width < Constants.BIG_SCREEN_THRESHOLD) bigFontValue else largeFontValue

            LazyColumn {
                item {
                    Spacer(modifier = Modifier.height(mediumSpacerValue))
                    Text(
                        text = stringResource(id = R.string.statistics),
                        color = MaterialTheme.colors.onSurface,
                        fontSize = if (width < Constants.BIG_SCREEN_THRESHOLD) smallMediumFontValue else mediumBigFontValue,
                        modifier = Modifier.padding(start = mediumPaddingValue)
                    )
                    Spacer(modifier = Modifier.height(mediumSpacerValue))
                }
                item {
                    Box(modifier = Modifier.padding(start = mediumPaddingValue)) {
                        StatisticsItem(
                            modifier = Modifier.width(width / 1.5f),
                            title = stringResource(R.string.total_watch_time_admin),
                            value = getFormattedTimeFromSeconds(
                                if (viewModel.watchTimeStats.isNotEmpty())
                                    viewModel.watchTimeStats[0].totalWatchTimeInSeconds else 0
                            ),
                            smallFontSize = smallFontSize,
                            largeFontSize = largeFontSize
                        )
                    }
                }

                item {

                    val height =
                        if (width < Constants.BIG_SCREEN_THRESHOLD) width / 2.25f else width / 4

                    Spacer(modifier = Modifier.height(mediumSpacerValue))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = mediumPaddingValue, end = mediumPaddingValue)
                    ) {
                        StatisticsItem(
                            modifier = Modifier
                                .height(height)
                                .weight(1f),
                            title = stringResource(id = R.string.users),
                            value = viewModel.userCount.value.toString(),
                            clickable = true,
                            smallFontSize = smallFontSize,
                            largeFontSize = largeFontSize
                        ) {
                            viewModel.onEvent(AdminScreenEvent.UsersDialogClicked)
                        }
                        Spacer(modifier = Modifier.width(tinyPaddingValue))
                        StatisticsItem(
                            modifier = Modifier
                                .height(height)
                                .weight(1f),
                            title = stringResource(id = R.string.films),
                            value = viewModel.filmCount.value.toString(),
                            clickable = true,
                            smallFontSize = smallFontSize,
                            largeFontSize = largeFontSize
                        ) {
                            viewModel.onEvent(AdminScreenEvent.FilmsDialogClicked)
                        }
                    }
                    Spacer(modifier = Modifier.height(mediumSpacerValue))
                    Divider(color = Red, thickness = 2.dp)
                }

                item {
                    if (viewModel.watchTimeStats.isNotEmpty())
                        Box(modifier = Modifier.padding(mediumPaddingValue)) {
                            Statistics(watchTimeStats = viewModel.watchTimeStats)
                        }
                }
            }
            if (viewModel.isUsersDialogVisible.value) {
                val users = viewModel.users.toList()
                    .filter {
                        it.email.lowercase()
                            .contains(viewModel.searchUserState.value.text.lowercase())
                    }
                val context = LocalContext.current
                UsersListDialog(users = users,
                    width = width,
                    userSearchState = viewModel.searchUserState.value,
                    onDismiss = {
                        focusManager.clearFocus()
                        viewModel.onEvent(AdminScreenEvent.UsersDialogDismissed)
                    },
                    onChangeFocus = {
                        viewModel.onEvent(AdminScreenEvent.UserSearchFocusChanged(it))
                    },
                    onValueChange = {
                        viewModel.onEvent(AdminScreenEvent.UsersDialogSearchEntered(it))
                    },
                    onDeleteUserClicked = {
                        focusManager.clearFocus()
                        scope.launch {
                            val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
                                message = context.getString(
                                    R.string.delete_user_with_email,
                                    it.email
                                ),
                                actionLabel = context.getString(R.string.delete),
                                duration = SnackbarDuration.Short,
                            )

                            when (snackbarResult) {
                                SnackbarResult.ActionPerformed -> {
                                    viewModel.onEvent(AdminScreenEvent.DeleteUserClicked(it.email))
                                }
                                else -> {
                                }
                            }
                        }
                    })
            }

            if (viewModel.isFilmsDialogVisible.value) {
                val films = mutableListOf<FilmFeedItem>()
                viewModel.films.forEach { filmFeedResponse ->
                    filmFeedResponse.films.forEach {
                        films.add(it)
                    }
                }
                val filteredFilms = films.filter {
                    it.name.lowercase().contains(viewModel.searchFilmState.value.text.lowercase())
                }

                FilmsListDialog(
                    films = filteredFilms,
                    width = width,
                    filmSearchState = viewModel.searchFilmState.value,
                    onSearchValueChange = {
                        viewModel.onEvent(AdminScreenEvent.FilmsDialogSearchEntered(it))
                    },
                    onSearchChangeFocus = {
                        viewModel.onEvent(AdminScreenEvent.FilmsSearchFocusChanged(it))
                    },
                    onDismiss = {
                        focusManager.clearFocus()
                        viewModel.onEvent(AdminScreenEvent.FilmsDialogDismissed)
                    },
                    onDeleteFilmClicked = {
                        scope.launch {
                            val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
                                message = "Изтриване на филм със заглавие: $it",
                                actionLabel = "Изтриване",
                                duration = SnackbarDuration.Short,
                            )

                            when (snackbarResult) {
                                SnackbarResult.ActionPerformed -> {
                                    viewModel.onEvent(AdminScreenEvent.DeleteFilmClicked(it))
                                }
                                else -> {
                                }
                            }
                        }
                    },
                    onAddFilmClicked = {
                        viewModel.onEvent(AdminScreenEvent.AddFilmClicked)
                    },
                    onFilmTitleValueChange = {
                        viewModel.onEvent(AdminScreenEvent.FilmTitleEntered(it))
                    },
                    onFilmTitleChangeFocus = {
                        viewModel.onEvent(AdminScreenEvent.FilmTitleFocusChanged(it))
                    },
                    onFilmDescriptionValueChange = {
                        viewModel.onEvent(AdminScreenEvent.FilmDescriptionEntered(it))
                    },
                    onFilmDescriptionChangeFocus = {
                        viewModel.onEvent(AdminScreenEvent.FilmDescriptionFocusChanged(it))
                    },
                    onFilmUrlValueChange = {
                        viewModel.onEvent(AdminScreenEvent.FilmUrlEntered(it))
                    },
                    onFilmUrlChangeFocus = {
                        viewModel.onEvent(AdminScreenEvent.FilmUrlFocusChanged(it))

                    },
                    onFilmCategoriesValueChange = {
                        viewModel.onEvent(AdminScreenEvent.FilmCategoriesEntered(it))
                    },
                    onFilmCategoriesChangeFocus = {
                        viewModel.onEvent(AdminScreenEvent.FilmCategoriesFocusChanged(it))
                    },
                    onFilmImageUrlsValueChange = {
                        viewModel.onEvent(AdminScreenEvent.FilmImagesEntered(it))
                    },
                    onFilmImageUrlsChangeFocus = {
                        viewModel.onEvent(AdminScreenEvent.FilmImagesFocusChanged(it))
                    },
                    filmTitleState = viewModel.filmTitleState.value,
                    filmDescriptionState = viewModel.filmDescriptionState.value,
                    filmUrlState = viewModel.filmUrlState.value,
                    filmCategoriesState = viewModel.filmCategoriesState.value,
                    filmImageUrlsState = viewModel.filmImageUrlsState.value,
                    isAddingFilm = viewModel.isAddFilmsDialogVisible.value,
                    onFilmSave = {
                        focusManager.clearFocus()
                        viewModel.onEvent(AdminScreenEvent.SaveFilm)
                    },
                )
            }
        }
        val context = LocalContext.current
        LaunchedEffect(key1 = viewModel.error.value) {
            if (viewModel.error.value.isNotBlank()) {
                scope.launch {
                    val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
                        message = viewModel.error.value,
                        actionLabel = context.getString(R.string.retry),
                        duration = SnackbarDuration.Indefinite,
                    )

                    when (snackbarResult) {
                        SnackbarResult.ActionPerformed -> {
                            viewModel.loadData()
                        }
                        else -> {
                        }
                    }
                }
            }
        }
    }

    BackHandler {
        if (viewModel.isUsersDialogVisible.value) {
            viewModel.onEvent(AdminScreenEvent.UsersDialogDismissed)
        } else {
            navController.navigateUp()
        }
    }
}