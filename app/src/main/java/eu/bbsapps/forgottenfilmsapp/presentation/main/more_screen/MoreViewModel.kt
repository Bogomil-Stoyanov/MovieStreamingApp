package eu.bbsapps.forgottenfilmsapp.presentation.main.more_screen

import android.content.SharedPreferences
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.bbsapps.forgottenfilmsapp.ForgottenFilmsApp.Companion.resource
import eu.bbsapps.forgottenfilmsapp.R
import eu.bbsapps.forgottenfilmsapp.common.Constants.KEY_LOGGED_IN_EMAIL
import eu.bbsapps.forgottenfilmsapp.common.Constants.KEY_PASSWORD
import eu.bbsapps.forgottenfilmsapp.common.Constants.NO_EMAIL
import eu.bbsapps.forgottenfilmsapp.common.Constants.NO_PASSWORD
import eu.bbsapps.forgottenfilmsapp.common.Resource
import eu.bbsapps.forgottenfilmsapp.data.remote.dto.responses.GenreWatchTimePair
import eu.bbsapps.forgottenfilmsapp.domain.use_case.account_management.*
import eu.bbsapps.forgottenfilmsapp.domain.use_case.admin.IsAdminUseCase
import eu.bbsapps.forgottenfilmsapp.domain.use_case.film.GetAllGenresUseCase
import eu.bbsapps.forgottenfilmsapp.presentation.components.OutlinedTextFieldState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MoreViewModel @Inject constructor(
    private val getNicknameUseCase: GetNicknameUseCase,
    private val changeNicknameUseCase: ChangeNicknameUseCase,
    private val updateGenresUseCase: UpdateGenresUseCase,
    private val getGenresUseCase: GetGenresUseCase,
    private val getAllGenresUseCase: GetAllGenresUseCase,
    private val getWatchTimeUseCase: GetWatchTimeUseCase,
    private val isAdminUseCase: IsAdminUseCase,
    private val changePasswordUseCase: ChangePasswordUseCase,
    private val sharedPref: SharedPreferences
) : ViewModel() {

    private val _nickname = mutableStateOf("")
    val nickname: State<String> = _nickname

    private val _error = mutableStateOf("")
    val error: State<String> = _error

    private val _info = mutableStateOf("")
    val info: State<String> = _info

    private val _isFavouriteCategoriesDialogVisible = mutableStateOf(false)
    val isFavouriteCategoriesDialogVisible: State<Boolean> = _isFavouriteCategoriesDialogVisible

    private val _isChangeNicknameDialogVisible = mutableStateOf(false)
    val isChangeNicknameDialogVisible: State<Boolean> = _isChangeNicknameDialogVisible

    private val _isPrivacyPolicyDialogVisible = mutableStateOf(false)
    val isPrivacyPolicyDialogVisible: State<Boolean> = _isPrivacyPolicyDialogVisible

    private val _changeNicknameState =
        mutableStateOf(OutlinedTextFieldState(hint = resource.getString(R.string.nickname)))
    val changeNicknameState: State<OutlinedTextFieldState> = _changeNicknameState

    private val _userGenres = mutableStateOf<List<String>>(emptyList())
    val userGenres: State<List<String>> = _userGenres

    private val _availableGeneres = mutableStateListOf<String>()
    val availableGeneres: SnapshotStateList<String> = _availableGeneres

    private val _watchTimeStats = mutableStateListOf<GenreWatchTimePair>()
    val watchTimeStats: SnapshotStateList<GenreWatchTimePair> = _watchTimeStats

    private val _isAdmin = mutableStateOf(false)
    val isAdmin: State<Boolean> = _isAdmin

    private val _changePasswordState =
        mutableStateOf(OutlinedTextFieldState(hint = resource.getString(R.string.changePassword)))
    val changePasswordState: State<OutlinedTextFieldState> = _changePasswordState

    private val _isChangePasswordDialogVisible = mutableStateOf(false)
    val isChangePasswordDialogVisible: State<Boolean> = _isChangePasswordDialogVisible

    init {
        getAllGenres()
        getWatchTimeStats()
        isAdmin()
    }

    fun onEvent(event: MoreScreenEvent) {
        when (event) {
            MoreScreenEvent.LogOutClicked -> {
                logOut()
            }
            MoreScreenEvent.FavouriteCategoriesClicked -> {
                getInterests()
                _isFavouriteCategoriesDialogVisible.value = true
            }
            MoreScreenEvent.FavouriteCategoriesDialogCanceled -> {
                _isFavouriteCategoriesDialogVisible.value = false
            }
            MoreScreenEvent.ChangeNicknameClicked -> {
                _isChangeNicknameDialogVisible.value = true
            }
            MoreScreenEvent.ChangeNicknameDialogCanceled -> {
                _isChangeNicknameDialogVisible.value = false
            }
            is MoreScreenEvent.EnteredNickname -> {
                _changeNicknameState.value = changeNicknameState.value.copy(text = event.value)

                if (changeNicknameState.value.text.isBlank()) {
                    _changeNicknameState.value =
                        changeNicknameState.value.copy(error = resource.getString(R.string.nickname_not_blank))
                } else {
                    if (changeNicknameState.value.text.isNotBlank()) {
                        _changeNicknameState.value = changeNicknameState.value.copy(error = "")
                    }
                }
            }
            is MoreScreenEvent.ChangeNicknameFocus -> {
                _changeNicknameState.value = changeNicknameState.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            changeNicknameState.value.text.isBlank()
                )
            }
            MoreScreenEvent.ChangeNicknameDialogSaved -> {
                if (changeNicknameState.value.text.isNotBlank()) {
                    saveNickname()
                    _changeNicknameState.value = changeNicknameState.value.copy(text = "")
                }
            }

            is MoreScreenEvent.InterestChangedInDialog -> {
                if (_userGenres.value.contains(event.interest)) {
                    _userGenres.value = _userGenres.value - event.interest
                } else {
                    _userGenres.value = _userGenres.value + event.interest
                }
            }
            MoreScreenEvent.FavouriteCategoriesDialogSaved -> {
                saveInterest()
            }
            MoreScreenEvent.PrivacyPolicyClicked -> {
                _isPrivacyPolicyDialogVisible.value = true
            }
            MoreScreenEvent.PrivacyPolicyDialogCanceled -> {
                _isPrivacyPolicyDialogVisible.value = false
            }
            MoreScreenEvent.ChangePasswordClicked -> {
                _isChangePasswordDialogVisible.value = true
            }
            is MoreScreenEvent.ChangePasswordFocus -> {
                _changePasswordState.value = changePasswordState.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            changePasswordState.value.text.isBlank()
                )
            }
            MoreScreenEvent.DismissPasswordDialog -> {
                _isChangePasswordDialogVisible.value = false
                _changePasswordState.value = changePasswordState.value.copy(text = "")
            }
            is MoreScreenEvent.EnteredPassword -> {
                _changePasswordState.value = changePasswordState.value.copy(text = event.value)

                val passwordPattern =
                    "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=_])(?=\\S+$).{8,}".toRegex()

                if (!passwordPattern.matches(changePasswordState.value.text)) {
                    _changePasswordState.value =
                        changePasswordState.value.copy(error = resource.getString(R.string.enter_valid_password))
                } else {
                    if (changePasswordState.value.error.isNotBlank()) {
                        _changePasswordState.value = changePasswordState.value.copy(error = "")
                    }
                }
            }
            MoreScreenEvent.SaveChangePasswordDialog -> {
                changePassword(changePasswordState.value.text)
            }
        }
    }

    private fun changePassword(newPassword: String) {
        changePasswordUseCase(newPassword).onEach { result ->
            if (result is Resource.Error) {
                _error.value = result.data?.message
                    ?: resource.getString(R.string.unknown_error_occurred)

            } else if (result is Resource.Success) {
                if (result.data?.successful == true) {
                    _info.value = resource.getString(R.string.successfully_changed_password)
                    logOut()
                } else {
                    _info.value =
                        result.data?.message ?: resource.getString(R.string.unknown_error_occurred)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun isAdmin() {
        isAdminUseCase().onEach { result ->
            if (result is Resource.Error) {
                _error.value = result.message
                    ?: resource.getString(R.string.unknown_error_occurred)

            } else if (result is Resource.Success) {
                _isAdmin.value = result.data ?: false
            }
        }.launchIn(viewModelScope)
    }

    private fun getAllGenres() {
        getAllGenresUseCase().onEach { result ->
            if (result is Resource.Error) {
                _error.value = result.message
                    ?: resource.getString(R.string.unknown_error_occurred)

            } else if (result is Resource.Success) {
                _availableGeneres.addAll(
                    if (result.data?.isNotEmpty() == true) {
                        result.data
                    } else emptyList()
                )
            }
        }.launchIn(viewModelScope)
    }

    private fun getWatchTimeStats() {
        getWatchTimeUseCase().onEach { result ->
            if (result is Resource.Error) {
                _error.value = result.message
                    ?: resource.getString(R.string.unknown_error_occurred)

            } else if (result is Resource.Success) {
                _watchTimeStats.clear()
                _watchTimeStats.addAll(
                    if (result.data?.isNotEmpty() == true) {
                        result.data
                    } else emptyList()
                )
            }
        }.launchIn(viewModelScope)
    }

    private fun saveNickname() {
        changeNicknameUseCase(_changeNicknameState.value.text).onEach { result ->
            if (result is Resource.Success) {
                getNickname()
            }
        }.launchIn(viewModelScope)
    }

    fun getNickname() {
        getNicknameUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _nickname.value = result.data?.message ?: ""
                    _error.value = ""
                }
                is Resource.Error -> {
                    _error.value =
                        result.data?.message ?: resource.getString(R.string.unknown_error_occurred)
                }
                else -> {
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun saveInterest() {
        updateGenresUseCase(userGenres.value).launchIn(viewModelScope)
    }

    private fun getInterests() {
        getGenresUseCase().onEach { result ->
            if (result is Resource.Success) {
                _userGenres.value = result.data ?: emptyList()
            }
        }.launchIn(viewModelScope)
    }

    private fun logOut() {
        sharedPref.edit().putString(KEY_LOGGED_IN_EMAIL, NO_EMAIL).apply()
        sharedPref.edit().putString(KEY_PASSWORD, NO_PASSWORD).apply()
    }

}