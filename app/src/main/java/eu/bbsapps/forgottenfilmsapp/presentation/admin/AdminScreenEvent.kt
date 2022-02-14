package eu.bbsapps.forgottenfilmsapp.presentation.admin

import androidx.compose.ui.focus.FocusState

sealed class AdminScreenEvent {
    object UsersDialogClicked : AdminScreenEvent()
    object UsersDialogDismissed : AdminScreenEvent()
    data class UsersDialogSearchEntered(val value: String) : AdminScreenEvent()
    data class UserSearchFocusChanged(val focusState: FocusState) : AdminScreenEvent()
    data class DeleteUserClicked(val email: String) : AdminScreenEvent()
    object FilmsDialogDismissed : AdminScreenEvent()
    object FilmsDialogClicked : AdminScreenEvent()
    data class FilmsDialogSearchEntered(val value: String) : AdminScreenEvent()
    data class FilmsSearchFocusChanged(val focusState: FocusState) : AdminScreenEvent()
    data class DeleteFilmClicked(val title: String) : AdminScreenEvent()
    object AddFilmClicked : AdminScreenEvent()
    data class FilmTitleEntered(val value: String) : AdminScreenEvent()
    data class FilmTitleFocusChanged(val focusState: FocusState) : AdminScreenEvent()
    data class FilmDescriptionEntered(val value: String) : AdminScreenEvent()
    data class FilmDescriptionFocusChanged(val focusState: FocusState) : AdminScreenEvent()
    data class FilmUrlEntered(val value: String) : AdminScreenEvent()
    data class FilmUrlFocusChanged(val focusState: FocusState) : AdminScreenEvent()
    data class FilmCategoriesEntered(val value: String) : AdminScreenEvent()
    data class FilmCategoriesFocusChanged(val focusState: FocusState) : AdminScreenEvent()
    data class FilmImagesEntered(val value: String) : AdminScreenEvent()
    data class FilmImagesFocusChanged(val focusState: FocusState) : AdminScreenEvent()
    object SaveFilm : AdminScreenEvent()
}

