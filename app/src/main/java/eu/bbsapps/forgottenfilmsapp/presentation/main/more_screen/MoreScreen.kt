package eu.bbsapps.forgottenfilmsapp.presentation.main.more_screen

import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import eu.bbsapps.forgottenfilmsapp.ForgottenFilmsApp.Companion.resource
import eu.bbsapps.forgottenfilmsapp.R
import eu.bbsapps.forgottenfilmsapp.common.Constants.BIG_SCREEN_THRESHOLD
import eu.bbsapps.forgottenfilmsapp.presentation.LockScreenOrientation
import eu.bbsapps.forgottenfilmsapp.presentation.Screen
import eu.bbsapps.forgottenfilmsapp.presentation.components.stats.Statistics
import eu.bbsapps.forgottenfilmsapp.presentation.main.components.BottomBar
import eu.bbsapps.forgottenfilmsapp.presentation.main.components.BottomBarEntry
import eu.bbsapps.forgottenfilmsapp.presentation.main.components.MainTopBar
import eu.bbsapps.forgottenfilmsapp.presentation.main.more_screen.dialogs.ChangeFavouriteCategoriesDialog
import eu.bbsapps.forgottenfilmsapp.presentation.main.more_screen.dialogs.ChangeNicknameDialog
import eu.bbsapps.forgottenfilmsapp.presentation.main.more_screen.dialogs.ChangePasswordDialog
import eu.bbsapps.forgottenfilmsapp.presentation.main.more_screen.dialogs.PrivacyPolicyDialog
import eu.bbsapps.forgottenfilmsapp.presentation.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MoreScreen(navController: NavController, viewModel: MoreViewModel = hiltViewModel()) {

    LaunchedEffect(key1 = true) {
        viewModel.getNickname()
    }

    val focusManager = LocalFocusManager.current
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

    val isStatisticsExpanded = remember {
        mutableStateOf(false)
    }
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
                currentlySelected = BottomBarEntry.MORE,
                navController = navController,
                onMoreClicked = {
                }
            )
        },
        scaffoldState = scaffoldState
    ) {
        BoxWithConstraints(Modifier.fillMaxSize()) {
            val width = maxWidth
            val fontSize = if (width < BIG_SCREEN_THRESHOLD) 18.sp else 26.sp
            val iconSize = if (width < BIG_SCREEN_THRESHOLD) smallIconSize else mediumIconSize

            Column(Modifier.padding(mediumPaddingValue)) {
                if (viewModel.error.value.isBlank())
                    Column {
                        Text(
                            text = stringResource(R.string.greeting, viewModel.nickname.value),
                            color = MaterialTheme.colors.onSurface,
                            fontSize = if (width < BIG_SCREEN_THRESHOLD) smallMediumFontValue else mediumBigFontValue
                        )
                        MoreListItem(
                            title = stringResource(id = R.string.favourite_genres),
                            icon = R.drawable.ic_interests,
                            fontSize = fontSize,
                            iconSize = iconSize
                        ) {
                            viewModel.onEvent(MoreScreenEvent.FavouriteCategoriesClicked)
                        }
                        MoreListItem(
                            title = stringResource(id = R.string.change_nickname),
                            icon = R.drawable.ic_manage_account,
                            fontSize = fontSize,
                            iconSize = iconSize
                        ) {
                            viewModel.onEvent(MoreScreenEvent.ChangeNicknameClicked)
                        }

                        MoreListItem(
                            title = stringResource(id = R.string.changePassword),
                            icon = R.drawable.ic_password,
                            fontSize = fontSize,
                            iconSize = iconSize
                        ) {
                            viewModel.onEvent(MoreScreenEvent.ChangePasswordClicked)
                        }

                        Row(
                            Modifier
                                .padding(top = mediumPaddingValue)
                                .clickable {
                                    isStatisticsExpanded.value = !isStatisticsExpanded.value
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            MoreListItem(
                                title = stringResource(id = R.string.statistics),
                                icon = R.drawable.ic_analytics,
                                fontSize = fontSize,
                                iconSize = iconSize
                            ) {
                                isStatisticsExpanded.value = !isStatisticsExpanded.value
                            }
                            Icon(
                                imageVector = if (isStatisticsExpanded.value)
                                    Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                contentDescription = "",
                                tint = MaterialTheme.colors.onSurface
                            )
                        }
                        AnimatedVisibility(visible = isStatisticsExpanded.value) {
                            Statistics(watchTimeStats = viewModel.watchTimeStats)
                        }
                        if (viewModel.isAdmin.value) {
                            MoreListItem(
                                title = stringResource(R.string.admin_panel),
                                icon = R.drawable.ic_admin_panel,
                                fontSize = fontSize,
                                iconSize = iconSize
                            ) {
                                navController.navigate(Screen.AdminPanelScreen.route)
                            }
                        }
                        val context = LocalContext.current
                        MoreListItem(
                            title = stringResource(id = R.string.feedback),
                            icon = R.drawable.ic_support,
                            fontSize = fontSize,
                            iconSize = iconSize
                        ) {
                            Intent(
                                Intent(
                                    Intent.ACTION_SENDTO,
                                    Uri.fromParts("mailto", "admin_forgotten_films@dir.bg", null)
                                )
                            ).apply {
                                putExtra(
                                    Intent.EXTRA_SUBJECT,
                                    resource.getString(R.string.feedback)
                                )
                                context.startActivity(this)
                            }
                        }
                    }

                Column {
                    MoreListItem(
                        title = stringResource(id = R.string.privacy_policy),
                        icon = R.drawable.ic_policy,
                        fontSize = fontSize,
                        iconSize = iconSize
                    ) {
                        viewModel.onEvent(MoreScreenEvent.PrivacyPolicyClicked)
                    }
                    MoreListItem(
                        title = stringResource(R.string.log_out),
                        icon = R.drawable.ic_logout,
                        fontSize = fontSize,
                        iconSize = iconSize
                    ) {
                        viewModel.onEvent(MoreScreenEvent.LogOutClicked)
                        navController.popBackStack()
                        navController.navigate(Screen.LoginScreen.route) {
                            popUpTo(Screen.HomeScreen.route) { inclusive = true }
                        }
                    }
                }
            }
        }

        if (viewModel.isChangeNicknameDialogVisible.value) {
            ChangeNicknameDialog(
                textFieldState = viewModel.changeNicknameState.value,
                onSave = {
                    viewModel.onEvent(MoreScreenEvent.ChangeNicknameDialogSaved)
                },
                onChangeFocus = {
                    viewModel.onEvent(MoreScreenEvent.ChangeNicknameFocus(it))
                },
                onValueChange = {
                    viewModel.onEvent(MoreScreenEvent.EnteredNickname(it))
                },
                onDismiss = {
                    focusManager.clearFocus()
                    viewModel.onEvent(MoreScreenEvent.ChangeNicknameDialogCanceled)
                }
            )
        }

        if (viewModel.isFavouriteCategoriesDialogVisible.value) {
            ChangeFavouriteCategoriesDialog(
                onDismiss = {
                    viewModel.onEvent(MoreScreenEvent.FavouriteCategoriesDialogCanceled)
                },
                selectedCategories = viewModel.userGenres.value,
                onCheck = {
                    viewModel.onEvent(MoreScreenEvent.InterestChangedInDialog(it))
                },
                onSave = {
                    viewModel.onEvent(MoreScreenEvent.FavouriteCategoriesDialogSaved)
                },
                genres = viewModel.availableGeneres
            )
        }

        if (viewModel.isPrivacyPolicyDialogVisible.value) {
            PrivacyPolicyDialog {
                viewModel.onEvent(MoreScreenEvent.PrivacyPolicyDialogCanceled)
            }
        }

        if (viewModel.isChangePasswordDialogVisible.value) {
            ChangePasswordDialog(
                textFieldState = viewModel.changePasswordState.value,
                onSave = {
                    viewModel.onEvent(MoreScreenEvent.SaveChangePasswordDialog)
                },
                onValueChange = { viewModel.onEvent(MoreScreenEvent.EnteredPassword(it)) },
                onChangeFocus = { viewModel.onEvent(MoreScreenEvent.ChangePasswordFocus(it)) },
                onDismiss = {
                    viewModel.onEvent(MoreScreenEvent.DismissPasswordDialog)
                    focusManager.clearFocus()
                }
            )
        }
    }

    if (viewModel.error.value.isNotBlank()) {
        val context = LocalContext.current
        LaunchedEffect(key1 = viewModel.error.value) {
            scope.launch {
                val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
                    message = viewModel.error.value,
                    actionLabel = context.getString(R.string.retry),
                    duration = SnackbarDuration.Indefinite,
                )

                if (snackbarResult == SnackbarResult.ActionPerformed) {
                    viewModel.getNickname()
                }
            }
        }
    }

    if (viewModel.info.value.isNotBlank()) {
        val context = LocalContext.current
        LaunchedEffect(key1 = viewModel.info.value) {
            scope.launch {
                scaffoldState.snackbarHostState.showSnackbar(viewModel.info.value)
            }
            if (viewModel.info.value == context.getString(R.string.successfully_changed_password)) {
                delay(1000L)
                navController.popBackStack()
                navController.navigate(Screen.LoginScreen.route) {
                    popUpTo(Screen.HomeScreen.route) { inclusive = true }
                }
            }
        }
    }
}

@Composable
fun MoreListItem(
    title: String,
    @DrawableRes icon: Int,
    fontSize: TextUnit,
    iconSize: Dp,
    onClick: () -> Unit
) {
    Spacer(modifier = Modifier.height(smallSpacerValue))
    Row(Modifier.clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = null
    ) { onClick() }) {
        Image(
            painterResource(id = icon),
            modifier = Modifier.size(iconSize),
            contentDescription = null,
            colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface)
        )
        Spacer(modifier = Modifier.width(smallSpacerValue))
        Text(text = title, color = MaterialTheme.colors.onSurface, fontSize = fontSize)
    }
}

