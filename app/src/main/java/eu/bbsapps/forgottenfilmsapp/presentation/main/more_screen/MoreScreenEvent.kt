package eu.bbsapps.forgottenfilmsapp.presentation.main.more_screen

import androidx.compose.ui.focus.FocusState

sealed class MoreScreenEvent {
    data class EnteredNickname(val value: String) : MoreScreenEvent()
    object LogOutClicked : MoreScreenEvent()
    object FavouriteCategoriesClicked : MoreScreenEvent()
    object FavouriteCategoriesDialogCanceled : MoreScreenEvent()
    object FavouriteCategoriesDialogSaved : MoreScreenEvent()
    object ChangeNicknameClicked : MoreScreenEvent()
    object ChangeNicknameDialogCanceled : MoreScreenEvent()
    object ChangeNicknameDialogSaved : MoreScreenEvent()
    object PrivacyPolicyClicked : MoreScreenEvent()
    object PrivacyPolicyDialogCanceled : MoreScreenEvent()
    data class ChangeNicknameFocus(val focusState: FocusState) : MoreScreenEvent()
    data class InterestChangedInDialog(val interest: String) : MoreScreenEvent()
}

