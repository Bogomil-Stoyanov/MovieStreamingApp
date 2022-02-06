package eu.bbsapps.forgottenfilmsapp.presentation.auth.register

import android.content.pm.ActivityInfo
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import eu.bbsapps.forgottenfilmsapp.R
import eu.bbsapps.forgottenfilmsapp.common.Constants.BIG_SCREEN_THRESHOLD
import eu.bbsapps.forgottenfilmsapp.common.TestTags.CONFIRM_PASSWORD_TEXT_FIELD
import eu.bbsapps.forgottenfilmsapp.common.TestTags.EMAIL_TEXT_FIELD
import eu.bbsapps.forgottenfilmsapp.common.TestTags.NICKNAME_TEXT_FIELD
import eu.bbsapps.forgottenfilmsapp.common.TestTags.PASSWORD_TEXT_FIELD
import eu.bbsapps.forgottenfilmsapp.common.TestTags.PRIVACY_POLICY_AGREEMENT
import eu.bbsapps.forgottenfilmsapp.common.TestTags.REGISTER_BUTTON
import eu.bbsapps.forgottenfilmsapp.presentation.LockScreenOrientation
import eu.bbsapps.forgottenfilmsapp.presentation.auth.components.GenresRegisterSelection
import eu.bbsapps.forgottenfilmsapp.presentation.auth.components.PasswordTextField
import eu.bbsapps.forgottenfilmsapp.presentation.components.OutlinedTextFieldWithHint
import eu.bbsapps.forgottenfilmsapp.presentation.main.more_screen.dialogs.PrivacyPolicyDialog
import eu.bbsapps.forgottenfilmsapp.presentation.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(navController: NavController, viewModel: RegisterViewModel = hiltViewModel()) {
    val emailState = viewModel.email.value
    val passwordState = viewModel.password.value
    val confirmPasswordState = viewModel.confirmPassword.value
    val nicknameState = viewModel.nickname.value
    val state = viewModel.state.value

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

    Scaffold(backgroundColor = MaterialTheme.colors.surface, scaffoldState = scaffoldState) {
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            val width = maxWidth
            val bigFontSize = if (width < BIG_SCREEN_THRESHOLD) bigFontValue else largeFontValue
            val fontSize = if (width < BIG_SCREEN_THRESHOLD) smallFontValue else mediumFontValue
            val mainRegisterColumnModifier =
                if (width < BIG_SCREEN_THRESHOLD) Modifier.fillMaxWidth()
                else Modifier.width(width * 0.75f)

            val contentHeight =
                if (width < BIG_SCREEN_THRESHOLD) contentHeightSmall else contentHeightMedium
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LazyColumn(
                    modifier = mainRegisterColumnModifier.padding(horizontal = bigSpacerValue),
                ) {
                    item {
                        Spacer(modifier = Modifier.height(bigSpacerValue))
                        Text(
                            text = stringResource(R.string.sign_up),
                            color = MaterialTheme.colors.onSurface,
                            fontSize = bigFontSize
                        )
                        Spacer(modifier = Modifier.height(mediumSpacerValue))
                    }
                    item {

                        OutlinedTextFieldWithHint(
                            modifier = Modifier
                                .height(contentHeight)
                                .testTag(EMAIL_TEXT_FIELD),
                            text = emailState.text,
                            hint = emailState.hint,
                            onValueChange = { viewModel.onEvent(RegisterEvent.EnteredEmail(it)) },
                            onFocusChange = {
                                viewModel.onEvent(RegisterEvent.ChangeEmailFocus(it))
                            },
                            isHintVisible = emailState.isHintVisible,
                            error = emailState.error,
                            isEmail = true,
                            fontSize = fontSize
                        )
                        Spacer(modifier = Modifier.height(mediumSpacerValue))
                    }

                    item {
                        PasswordTextField(
                            modifier = Modifier
                                .height(contentHeight)
                                .testTag(PASSWORD_TEXT_FIELD),
                            text = passwordState.text,
                            hint = passwordState.hint,
                            onValueChange = { viewModel.onEvent(RegisterEvent.EnteredPassword(it)) },
                            onFocusChange = {
                                viewModel.onEvent(RegisterEvent.ChangePasswordFocus(it))
                            },
                            isHintVisible = passwordState.isHintVisible,
                            isPasswordVisible = passwordState.isPasswordVisible,
                            onPasswordVisibilityChanged = {
                                viewModel.onEvent(RegisterEvent.ChangePasswordVisibility(it))
                            },
                            error = passwordState.error,
                            fontSize = fontSize
                        )
                        Spacer(modifier = Modifier.height(mediumSpacerValue))
                    }
                    item {
                        PasswordTextField(
                            modifier = Modifier
                                .height(contentHeight)
                                .testTag(CONFIRM_PASSWORD_TEXT_FIELD),
                            text = confirmPasswordState.text,
                            hint = confirmPasswordState.hint,
                            onValueChange = {
                                viewModel.onEvent(
                                    RegisterEvent.EnteredConfirmPassword(
                                        it
                                    )
                                )
                            },
                            onFocusChange = {
                                viewModel.onEvent(RegisterEvent.ChangeConfirmPasswordFocus(it))
                            },
                            isHintVisible = confirmPasswordState.isHintVisible,
                            isPasswordVisible = confirmPasswordState.isPasswordVisible,
                            onPasswordVisibilityChanged = {
                                viewModel.onEvent(RegisterEvent.ChangeConfirmPasswordVisibility(it))
                            },
                            error = confirmPasswordState.error,
                            fontSize = fontSize
                        )
                        Spacer(modifier = Modifier.height(mediumSpacerValue))
                    }

                    item {
                        Divider(color = MaterialTheme.colors.primary)
                        Spacer(modifier = Modifier.height(mediumSpacerValue))
                        OutlinedTextFieldWithHint(
                            modifier = Modifier
                                .height(contentHeight)
                                .testTag(NICKNAME_TEXT_FIELD),
                            text = nicknameState.text,
                            hint = nicknameState.hint,
                            onValueChange = { viewModel.onEvent(RegisterEvent.EnteredNickname(it)) },
                            onFocusChange = {
                                viewModel.onEvent(RegisterEvent.ChangeNicknameFocus(it))
                            },
                            isHintVisible = nicknameState.isHintVisible,
                            error = nicknameState.error,
                            fontSize = fontSize
                        )
                        Spacer(modifier = Modifier.height(mediumSpacerValue))
                    }

                    item {
                        Text(
                            text = stringResource(R.string.select_interests),
                            color = MaterialTheme.colors.onSurface,
                            fontSize = fontSize
                        )
                        GenresRegisterSelection(
                            currentlySelected = viewModel.selectedInterests,
                            onCheck = {
                                viewModel.onEvent(RegisterEvent.ChangedInterest(it))
                            },
                            fontSize = fontSize,
                            genres = viewModel.generes
                        )
                        Spacer(modifier = Modifier.height(mediumSpacerValue))
                    }

                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(tinySpacerValue),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            val privacyPolicyFont =
                                if (width < BIG_SCREEN_THRESHOLD) 14.sp else 20.sp
                            Row(
                                modifier = Modifier
                                    .testTag(PRIVACY_POLICY_AGREEMENT)
                                    .clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = null
                                    ) {
                                        viewModel.onEvent(RegisterEvent.PrivacyPolicyAgreeClicked)
                                    },
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Checkbox(
                                    checked = viewModel.agreesToPrivacyPolicy.value,
                                    onCheckedChange = null,
                                    colors = CheckboxDefaults.colors(
                                        uncheckedColor = MaterialTheme.colors.onSurface.copy(alpha = 0.7f),
                                        checkedColor = MaterialTheme.colors.primary
                                    ),
                                )
                                Spacer(modifier = Modifier.width(tinySpacerValue))
                                Text(
                                    text = stringResource(id = R.string.privacy_policy_agree) + " ",
                                    color = MaterialTheme.colors.onSurface,
                                    fontSize = privacyPolicyFont
                                )
                            }
                            Text(
                                text = stringResource(id = R.string.privacy_policy),
                                color = MaterialTheme.colors.onSurface,
                                style = TextStyle(
                                    fontSize = privacyPolicyFont,
                                    textDecoration = TextDecoration.Underline
                                ),
                                modifier = Modifier.clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                ) {
                                    viewModel.onEvent(RegisterEvent.PrivacyPolicyClicked)
                                })
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(smallSpacerValue))
                        val focusManager = LocalFocusManager.current
                        Button(
                            onClick = {
                                focusManager.clearFocus()
                                viewModel.onEvent(RegisterEvent.RegisterClicked)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(contentHeight - 10.dp)
                                .clip(
                                    RoundedCornerShape(smallRoundedCornerValue)
                                )
                                .testTag(REGISTER_BUTTON),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = MaterialTheme.colors.primary,
                                disabledBackgroundColor = Red.copy(0.4f)
                            ),
                            enabled = viewModel.isRegisterButtonEnabled.value
                        ) {
                            Text(text = stringResource(id = R.string.sign_up), fontSize = fontSize)
                        }
                        Spacer(modifier = Modifier.height(mediumSpacerValue))
                    }

                    item {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.Bottom,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(
                                text = stringResource(R.string.already_have_acc) + " ",
                                color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f),
                                fontSize = fontSize
                            )
                            Text(
                                text = stringResource(R.string.sign_in_here),
                                style = TextStyle(
                                    textDecoration = TextDecoration.Underline,
                                    fontSize = fontSize
                                ),
                                color = MaterialTheme.colors.onSurface,
                                modifier = Modifier.clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                ) {
                                    navController.navigateUp()
                                }
                            )
                        }
                        Spacer(modifier = Modifier.height(mediumSpacerValue))
                    }
                }
            }


            if (state.isLoading) {
                Spacer(modifier = Modifier.height(mediumSpacerValue))
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    CircularProgressIndicator()
                }
            }

            if (viewModel.isPrivacyPolicyDialogVisible.value) {
                PrivacyPolicyDialog {
                    viewModel.onEvent(RegisterEvent.PrivacyPolicyDialogCanceled)
                }
            }

            if (state.error.isNotBlank()) {
                LaunchedEffect(key1 = state.error) {
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(message = state.error)
                    }
                }
            }
            val context = LocalContext.current
            state.registerSuccessful?.let {
                if (it) {
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(message = context.getString(R.string.you_signed_up))
                        navController.navigateUp()
                    }
                }
            }
        }
    }
}