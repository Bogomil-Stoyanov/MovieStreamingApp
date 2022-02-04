package eu.bbsapps.forgottenfilmsapp.presentation.film.details

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import eu.bbsapps.forgottenfilmsapp.R
import eu.bbsapps.forgottenfilmsapp.common.Constants.BIG_SCREEN_THRESHOLD
import eu.bbsapps.forgottenfilmsapp.presentation.LockScreenOrientation
import eu.bbsapps.forgottenfilmsapp.presentation.Screen
import eu.bbsapps.forgottenfilmsapp.presentation.film.details.components.FilmDetailsTopBar
import eu.bbsapps.forgottenfilmsapp.presentation.film.details.components.FilmGenres
import eu.bbsapps.forgottenfilmsapp.presentation.film.details.components.FilmImages
import eu.bbsapps.forgottenfilmsapp.presentation.film.details.components.ImageGallery
import eu.bbsapps.forgottenfilmsapp.presentation.ui.theme.*
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun FilmDetailsScreen(
    navController: NavController,
    viewModel: FilmDetailsViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val isFilmAddedToList = viewModel.isFilmAddedToList.value

    val systemUiController: SystemUiController = rememberSystemUiController()
    systemUiController.isStatusBarVisible = true
    systemUiController.isNavigationBarVisible = true
    systemUiController.isSystemBarsVisible = true

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

    BoxWithConstraints(Modifier.fillMaxSize()) {
        val width = maxWidth
        val floatingActionButtonSize = if (width < BIG_SCREEN_THRESHOLD) 56.dp else 80.dp
        Scaffold(
            backgroundColor = MaterialTheme.colors.background,
            floatingActionButton = {
                if (state.error.isBlank() && !state.isLoading)
                    FloatingActionButton(
                        onClick = {
                            viewModel.onEvent(FilmDetailsEvent.GalleryClosed)
                            val encodedUrl = URLEncoder.encode(
                                "${state.film?.url}",
                                StandardCharsets.UTF_8.toString()
                            )
                            navController.navigate(
                                Screen.FilmWatchScreen.route + "/${encodedUrl}/${
                                    state.film?.categories?.get(0) ?: ""
                                }"
                            )
                        },
                        backgroundColor = MaterialTheme.colors.primary,
                        modifier = Modifier.size(floatingActionButtonSize)
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = stringResource(R.string.play)
                        )
                    }
            },
            topBar = {
                FilmDetailsTopBar {
                    navController.navigateUp()
                }
            },
            scaffoldState = scaffoldState
        ) {
            if (state.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            LazyColumn(Modifier.fillMaxSize()) {
                state.film?.let {
                    item {
                        FilmImages(imageUrls = it.imageUrls, width = width) {
                            viewModel.onEvent(FilmDetailsEvent.ClickedImage(it))
                        }
                        Spacer(modifier = Modifier.height(smallSpacerValue))
                    }
                    item {
                        Column(Modifier.padding(mediumPaddingValue)) {
                            val fontSize =
                                if (width < BIG_SCREEN_THRESHOLD) smallMediumFontValue else mediumBigFontValue
                            Text(
                                text = it.name,
                                color = MaterialTheme.colors.onSurface,
                                fontSize = fontSize
                            )
                        }
                    }

                    item {
                        Column {
                            Divider(Modifier.fillMaxWidth(), color = MaterialTheme.colors.surface)
                            Row(
                                horizontalArrangement = Arrangement.SpaceAround,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(mediumPaddingValue)
                            ) {
                                val iconSize =
                                    if (width < BIG_SCREEN_THRESHOLD) mediumIconSize else largeIconSize
                                val fontSize =
                                    if (width < BIG_SCREEN_THRESHOLD) smallFontValue else mediumFontValue
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    IconButton(onClick = {
                                        viewModel.onEvent(FilmDetailsEvent.LikeClicked)
                                    }) {
                                        Icon(
                                            imageVector = Icons.Default.ThumbUp,
                                            contentDescription = null,
                                            modifier = Modifier.size(iconSize),
                                            tint = if (it.isLiked == 1) MaterialTheme.colors.onSurface else MaterialTheme.colors.onSurface.copy(
                                                0.4f
                                            )
                                        )
                                    }
                                    Text(
                                        text = "${it.likes}",
                                        color = MaterialTheme.colors.onSurface,
                                        fontSize = fontSize
                                    )
                                }

                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    IconButton(onClick = {
                                        viewModel.onEvent(FilmDetailsEvent.DislikeClicked)
                                    }) {
                                        Icon(
                                            imageVector = Icons.Default.ThumbUp,
                                            contentDescription = null,
                                            modifier = Modifier
                                                .size(iconSize)
                                                .rotate(180f),
                                            tint = if (it.isLiked == -1) MaterialTheme.colors.onSurface else MaterialTheme.colors.onSurface.copy(
                                                0.4f
                                            )
                                        )
                                    }
                                    Text(
                                        text = "${it.dislikes}",
                                        color = MaterialTheme.colors.onSurface,
                                        fontSize = fontSize
                                    )
                                }

                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    val context = LocalContext.current
                                    val appName = stringResource(id = R.string.app_name)
                                    IconButton(onClick = {
                                        val sendIntent: Intent = Intent().apply {
                                            action = Intent.ACTION_SEND
                                            putExtra(
                                                Intent.EXTRA_TEXT,
                                                "Гледай ${state.film.name} в $appName!"
                                            )
                                            type = "text/plain"
                                        }
                                        val shareIntent = Intent.createChooser(sendIntent, null)
                                        context.startActivity(shareIntent)
                                    }) {
                                        Icon(
                                            imageVector = Icons.Default.Share,
                                            contentDescription = null,
                                            modifier = Modifier.size(iconSize),
                                            tint = MaterialTheme.colors.onSurface
                                        )
                                    }
                                    Text(
                                        text = stringResource(R.string.share),
                                        color = MaterialTheme.colors.onSurface,
                                        fontSize = fontSize
                                    )
                                }

                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    IconButton(onClick = {
                                        viewModel.onEvent(FilmDetailsEvent.AddToList)
                                    }) {
                                        Icon(
                                            painter = painterResource(
                                                id = if (isFilmAddedToList) R.drawable.ic_playlist_check
                                                else R.drawable.ic_playlist_plus
                                            ),
                                            contentDescription = null,
                                            modifier = Modifier.size(iconSize),
                                            tint = MaterialTheme.colors.onSurface
                                        )
                                    }
                                    Text(
                                        text = if (isFilmAddedToList) stringResource(R.string.added_to_list)
                                        else stringResource(R.string.add_to_list),
                                        color = MaterialTheme.colors.onSurface, fontSize = fontSize
                                    )
                                }


                            }
                            Divider(Modifier.fillMaxWidth(), color = MaterialTheme.colors.surface)
                        }
                    }
                    item {
                        FilmGenres(genres = it.categories, width = width)
                        Divider(Modifier.fillMaxWidth(), color = MaterialTheme.colors.surface)
                    }

                    item {
                        val fontSize =
                            if (width < BIG_SCREEN_THRESHOLD) smallFontValue else mediumFontValue
                        Column(Modifier.padding(mediumPaddingValue)) {
                            Text(
                                text = stringResource(R.string.description),
                                color = MaterialTheme.colors.onSurface,
                                fontSize = fontSize
                            )
                            Text(
                                text = it.description,
                                color = MaterialTheme.colors.onSurface,
                                fontSize = fontSize
                            )
                            Spacer(modifier = Modifier.height(100.dp))
                        }
                    }

                }
            }
        }
        BackHandler {
            if (viewModel.isGalleryVisible.value) {
                viewModel.onEvent(FilmDetailsEvent.GalleryClosed)
            } else {
                navController.navigateUp()
            }
        }
    }

    if (viewModel.isGalleryVisible.value) {
        ImageGallery(
            onNextClicked = { viewModel.onEvent(FilmDetailsEvent.NextImageClicked) },
            onPreviousClicked = { viewModel.onEvent(FilmDetailsEvent.PreviousImageClicked) },
            onClose = { viewModel.onEvent(FilmDetailsEvent.GalleryClosed) },
            currentImageUrl = viewModel.currentImageUrl.value
        )
    }

    if (state.error.isNotBlank()) {
        LaunchedEffect(key1 = state.error) {
            scope.launch {
                scaffoldState.snackbarHostState.showSnackbar(state.error)
            }
        }
    }
}
