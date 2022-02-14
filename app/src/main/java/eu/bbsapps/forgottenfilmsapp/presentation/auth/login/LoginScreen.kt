package eu.bbsapps.forgottenfilmsapp.presentation.auth.login

import android.content.pm.ActivityInfo
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import eu.bbsapps.forgottenfilmsapp.R
import eu.bbsapps.forgottenfilmsapp.common.Constants.BIG_SCREEN_THRESHOLD
import eu.bbsapps.forgottenfilmsapp.common.TestTags.EMAIL_TEXT_FIELD
import eu.bbsapps.forgottenfilmsapp.common.TestTags.LOGIN_BUTTON
import eu.bbsapps.forgottenfilmsapp.common.TestTags.PASSWORD_TEXT_FIELD
import eu.bbsapps.forgottenfilmsapp.common.TestTags.REMEMBER_ME_CHECKBOX
import eu.bbsapps.forgottenfilmsapp.presentation.LockScreenOrientation
import eu.bbsapps.forgottenfilmsapp.presentation.Screen
import eu.bbsapps.forgottenfilmsapp.presentation.auth.components.PasswordTextField
import eu.bbsapps.forgottenfilmsapp.presentation.auth.login.dialogs.ForgottenPasswordDialog
import eu.bbsapps.forgottenfilmsapp.presentation.components.OutlinedTextFieldWithHint
import eu.bbsapps.forgottenfilmsapp.presentation.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navController: NavController, viewModel: LoginViewModel = hiltViewModel()) {

    val emailState = viewModel.email.value
    val passwordState = viewModel.password.value
    val state = viewModel.state.value
    val rememberMe = viewModel.rememberMe.value

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

    Scaffold(backgroundColor = MaterialTheme.colors.surface, scaffoldState = scaffoldState) {
        BoxWithConstraints(Modifier.fillMaxWidth()) {
            val width = maxWidth
            val mainLoginColumnModifier =
                if (width < BIG_SCREEN_THRESHOLD) Modifier.fillMaxWidth()
                else Modifier.width(width * 0.75f)
            val contentHeight =
                if (width < BIG_SCREEN_THRESHOLD) contentHeightSmall else contentHeightMedium
            var fontSize = if (width < BIG_SCREEN_THRESHOLD) bigFontValue else largeFontValue

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = mainLoginColumnModifier.padding(bigSpacerValue),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column(Modifier.fillMaxWidth()) {
                        Text(
                            text = stringResource(R.string.welcome),
                            color = MaterialTheme.colors.onSurface,
                            fontSize = fontSize
                        )
                        Spacer(modifier = Modifier.height(smallSpacerValue))
                        fontSize =
                            if (width < BIG_SCREEN_THRESHOLD) smallFontValue else mediumFontValue
                        Text(
                            text = stringResource(R.string.sign_in_subtitle),
                            color = MaterialTheme.colors.onSurface,
                            fontSize = fontSize
                        )
                    }
                    Spacer(modifier = Modifier.height(mediumSpacerValue))
                    OutlinedTextFieldWithHint(
                        modifier = Modifier
                            .height(contentHeight)
                            .testTag(EMAIL_TEXT_FIELD),
                        text = emailState.text,
                        hint = emailState.hint,
                        onValueChange = { viewModel.onEvent(LoginEvent.EnteredEmail(it)) },
                        onFocusChange = {
                            viewModel.onEvent(LoginEvent.ChangeEmailFocus(it))
                        },
                        isHintVisible = emailState.isHintVisible,
                        isEmail = true,
                        fontSize = fontSize
                    )
                    Spacer(modifier = Modifier.height(mediumSpacerValue))
                    PasswordTextField(
                        modifier = Modifier
                            .height(contentHeight)
                            .testTag(PASSWORD_TEXT_FIELD),
                        text = passwordState.text,
                        hint = passwordState.hint,
                        onValueChange = { viewModel.onEvent(LoginEvent.EnteredPassword(it)) },
                        onFocusChange = {
                            viewModel.onEvent(LoginEvent.ChangePasswordFocus(it))
                        },
                        isHintVisible = passwordState.isHintVisible,
                        isPasswordVisible = passwordState.isPasswordVisible,
                        onPasswordVisibilityChanged = {
                            viewModel.onEvent(
                                LoginEvent.ChangePasswordVisibility(
                                    it
                                )
                            )
                        },
                        fontSize = fontSize
                    )
                    Spacer(modifier = Modifier.height(mediumSpacerValue))
                    Button(
                        onClick = {
                            focusManager.clearFocus()
                            viewModel.onEvent(LoginEvent.LoginClicked)
                        }, modifier = Modifier
                            .fillMaxWidth()
                            .height(contentHeight - 10.dp)
                            .clip(
                                RoundedCornerShape(smallRoundedCornerValue)
                            )
                            .testTag(LOGIN_BUTTON),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.primary,
                            disabledBackgroundColor = Red.copy(0.4f)
                        ),
                        enabled = viewModel.email.value.text.isNotBlank() && viewModel.password.value.text.isNotBlank()
                    ) {
                        Text(text = stringResource(id = R.string.sign_in), fontSize = fontSize)
                    }
                    Spacer(modifier = Modifier.height(mediumSpacerValue))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            )
                            { viewModel.onEvent(LoginEvent.RememberMeClicked) }
                            .testTag(REMEMBER_ME_CHECKBOX),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = rememberMe,
                            onCheckedChange = null,
                            colors = CheckboxDefaults.colors(
                                uncheckedColor = MaterialTheme.colors.onSurface.copy(alpha = 0.7f),
                                checkedColor = MaterialTheme.colors.primary
                            ),
                        )
                        Spacer(modifier = Modifier.width(tinySpacerValue))
                        Text(
                            text = stringResource(R.string.remember_me),
                            color = MaterialTheme.colors.onSurface,
                            modifier = Modifier.clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) { viewModel.onEvent(LoginEvent.RememberMeClicked) },
                            fontSize = fontSize
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = largeSpacerValue),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        fontSize =
                            if (width < BIG_SCREEN_THRESHOLD) smallFontValue else mediumFontValue
                        Text(
                            text = stringResource(R.string.dont_have_account) + " ",
                            color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f),
                            fontSize = fontSize
                        )
                        Text(
                            text = stringResource(R.string.sign_up_here),
                            style = TextStyle(
                                textDecoration = TextDecoration.Underline,
                                fontSize = fontSize
                            ),
                            color = MaterialTheme.colors.onSurface,
                            modifier = Modifier.clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                navController.navigate(Screen.RegisterScreen.route)
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(tinySpacerValue))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.forgottenPassword) + "?",
                            style = TextStyle(
                                textDecoration = TextDecoration.Underline,
                                fontSize = fontSize
                            ),
                            modifier = Modifier.clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                viewModel.onEvent(LoginEvent.ForgottenPasswordClicked)
                            }
                        )
                    }
                }
            }
        }

        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Spacer(modifier = Modifier.height(mediumSpacerValue))
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    CircularProgressIndicator()
                }
            }
        }

        if (state.error.isNotBlank()) {
            LaunchedEffect(key1 = state.error) {
                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(message = state.error)
                }
            }
        }

        if (viewModel.forgottenPasswordDialogVisible.value) {
            ForgottenPasswordDialog(
                textFieldState = viewModel.forgottenEmail.value,
                onSave = { viewModel.onEvent(LoginEvent.SendResetPasswordClicked) },
                onValueChange = { viewModel.onEvent(LoginEvent.EnteredForgottenPasswordEmail(it)) },
                onChangeFocus = { viewModel.onEvent(LoginEvent.ChangeForgottenPasswordEmailFocus(it)) },
                onDismiss = {
                    viewModel.onEvent(LoginEvent.ForgottenPasswordDialogDismissed)
                    focusManager.clearFocus()
                }
            )
        }

        state.loginSuccessful?.let { isLoggedIn ->
            if (isLoggedIn) {
                LaunchedEffect(key1 = isLoggedIn) {
                    navController.popBackStack()
                    navController.navigate(Screen.HomeScreen.route)
                }
            }
        }
    }
}