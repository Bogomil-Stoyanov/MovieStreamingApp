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
}
