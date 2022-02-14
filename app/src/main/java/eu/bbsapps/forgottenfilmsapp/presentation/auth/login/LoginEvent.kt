package eu.bbsapps.forgottenfilmsapp.presentation.auth.login

import androidx.compose.ui.focus.FocusState

sealed class LoginEvent {
    data class EnteredEmail(val value: String) : LoginEvent()
    data class EnteredPassword(val value: String) : LoginEvent()
    data class ChangeEmailFocus(val focusState: FocusState) : LoginEvent()
    data class ChangePasswordFocus(val focusState: FocusState) : LoginEvent()
    data class ChangePasswordVisibility(val isVisible: Boolean) : LoginEvent()
    object RememberMeClicked : LoginEvent()
    object LoginClicked : LoginEvent()
    object ForgottenPasswordClicked : LoginEvent()
    object ForgottenPasswordDialogDismissed : LoginEvent()
    object SendResetPasswordClicked : LoginEvent()
    data class EnteredForgottenPasswordEmail(val value: String) : LoginEvent()
    data class ChangeForgottenPasswordEmailFocus(val focusState: FocusState) : LoginEvent()
}
