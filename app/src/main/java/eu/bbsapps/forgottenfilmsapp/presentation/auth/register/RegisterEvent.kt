package eu.bbsapps.forgottenfilmsapp.presentation.auth.register

import androidx.compose.ui.focus.FocusState

sealed class RegisterEvent {
    data class EnteredEmail(val value: String) : RegisterEvent()
    data class EnteredPassword(val value: String) : RegisterEvent()
    data class EnteredConfirmPassword(val value: String) : RegisterEvent()
    data class EnteredNickname(val value: String) : RegisterEvent()
    data class ChangeEmailFocus(val focusState: FocusState) : RegisterEvent()
    data class ChangePasswordFocus(val focusState: FocusState) : RegisterEvent()
    data class ChangePasswordVisibility(val isVisible: Boolean) : RegisterEvent()
    data class ChangeConfirmPasswordFocus(val focusState: FocusState) : RegisterEvent()
    data class ChangeConfirmPasswordVisibility(val isVisible: Boolean) : RegisterEvent()
    data class ChangeNicknameFocus(val focusState: FocusState) : RegisterEvent()
    data class ChangedInterest(val interest: String) : RegisterEvent()
    object RegisterClicked : RegisterEvent()
    object PrivacyPolicyClicked : RegisterEvent()
    object PrivacyPolicyDialogCanceled : RegisterEvent()
    object PrivacyPolicyAgreeClicked : RegisterEvent()
}
