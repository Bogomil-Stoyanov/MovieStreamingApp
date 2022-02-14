package eu.bbsapps.forgottenfilmsapp.presentation.admin

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.bbsapps.forgottenfilmsapp.ForgottenFilmsApp.Companion.resource
import eu.bbsapps.forgottenfilmsapp.R
import eu.bbsapps.forgottenfilmsapp.common.Resource
import eu.bbsapps.forgottenfilmsapp.data.remote.dto.requests.admin.Film
import eu.bbsapps.forgottenfilmsapp.data.remote.dto.responses.FilmFeedResponse
import eu.bbsapps.forgottenfilmsapp.data.remote.dto.responses.GenreWatchTimePair
import eu.bbsapps.forgottenfilmsapp.data.remote.dto.responses.UserResponse
import eu.bbsapps.forgottenfilmsapp.domain.use_case.admin.*
import eu.bbsapps.forgottenfilmsapp.domain.use_case.film.GetAllFilmsUseCase
import eu.bbsapps.forgottenfilmsapp.presentation.components.OutlinedTextFieldState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AdminViewModel @Inject constructor(
    private val getFilmsCountUseCase: GetFilmsCountUseCase,
    private val getTotalWatchTimeAdminUseCase: GetTotalWatchTimeAdminUseCase,
    private val getUsersCountUseCase: GetUsersCountUseCase,
    private val getAllUsersUseCase: GetAllUsersUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    private val getAllFilmsUseCase: GetAllFilmsUseCase,
    private val deleteFilmUseCase: DeleteFilmUseCase,
    private val addFilmsUseCase: AddFilmsUseCase
) : ViewModel() {

    private val _filmCount = mutableStateOf(0)
    val filmCount: State<Int> = _filmCount

    private val _userCount = mutableStateOf(0)
    val userCount: State<Int> = _userCount

    private val _watchTimeStats = mutableStateListOf<GenreWatchTimePair>()
    val watchTimeStats: SnapshotStateList<GenreWatchTimePair> = _watchTimeStats

    private val _error = mutableStateOf("")
    val error: State<String> = _error

    private val _isUsersDialogVisible = mutableStateOf(false)
    val isUsersDialogVisible: State<Boolean> = _isUsersDialogVisible

    private val _isFilmsDialogVisible = mutableStateOf(false)
    val isFilmsDialogVisible: State<Boolean> = _isFilmsDialogVisible

    private val _isAddFilmsDialogVisible = mutableStateOf(false)
    val isAddFilmsDialogVisible: State<Boolean> = _isAddFilmsDialogVisible

    private val _searchUserState =
        mutableStateOf(OutlinedTextFieldState(hint = resource.getString(R.string.email)))
    val searchUserState: State<OutlinedTextFieldState> = _searchUserState

    private val _searchFilmState =
        mutableStateOf(OutlinedTextFieldState(hint = resource.getString(R.string.film_name)))
    val searchFilmState: State<OutlinedTextFieldState> = _searchFilmState

    private val _filmTitleState =
        mutableStateOf(OutlinedTextFieldState(hint = resource.getString(R.string.film_name)))
    val filmTitleState: State<OutlinedTextFieldState> = _filmTitleState

    private val _filmDescriptionState =
        mutableStateOf(OutlinedTextFieldState(hint = resource.getString(R.string.film_description)))
    val filmDescriptionState: State<OutlinedTextFieldState> = _filmDescriptionState

    private val _filmUrlState =
        mutableStateOf(OutlinedTextFieldState(hint = resource.getString(R.string.film_url)))
    val filmUrlState: State<OutlinedTextFieldState> = _filmUrlState

    private val _filmCategoriesState =
        mutableStateOf(OutlinedTextFieldState(hint = resource.getString(R.string.film_categories)))
    val filmCategoriesState: State<OutlinedTextFieldState> = _filmCategoriesState

    private val _filmImageUrlsState =
        mutableStateOf(OutlinedTextFieldState(hint = resource.getString(R.string.film_image_urls)))
    val filmImageUrlsState: State<OutlinedTextFieldState> = _filmImageUrlsState

    private val _users = mutableStateListOf<UserResponse>()
    val users: SnapshotStateList<UserResponse> = _users

    private val _films = mutableStateListOf<FilmFeedResponse>()
    val films: SnapshotStateList<FilmFeedResponse> = _films

    init {
        loadData()
    }

    fun onEvent(event: AdminScreenEvent) {
        when (event) {
            AdminScreenEvent.UsersDialogClicked -> {
                _isUsersDialogVisible.value = true
            }
            AdminScreenEvent.UsersDialogDismissed -> {
                _searchUserState.value = searchFilmState.value.copy(text = "")
                _isUsersDialogVisible.value = false
            }
            is AdminScreenEvent.UserSearchFocusChanged -> {
                _searchUserState.value = searchUserState.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            searchUserState.value.text.isBlank()
                )
            }
            is AdminScreenEvent.UsersDialogSearchEntered -> {
                _searchUserState.value = searchUserState.value.copy(text = event.value)
            }
            is AdminScreenEvent.DeleteUserClicked -> {
                deleteUser(event.email)
            }
            AdminScreenEvent.FilmsDialogClicked -> {
                _isFilmsDialogVisible.value = true
            }
            AdminScreenEvent.FilmsDialogDismissed -> {
                _searchFilmState.value = searchFilmState.value.copy(text = "")
                _filmTitleState.value = filmTitleState.value.copy(text = "")
                _filmDescriptionState.value = filmDescriptionState.value.copy(text = "")
                _filmCategoriesState.value = filmCategoriesState.value.copy(text = "")
                _filmUrlState.value = filmUrlState.value.copy(text = "")
                _filmImageUrlsState.value = filmImageUrlsState.value.copy(text = "")
                _isFilmsDialogVisible.value = false
                _isAddFilmsDialogVisible.value = false
            }
            is AdminScreenEvent.FilmsDialogSearchEntered -> {
                _searchFilmState.value = searchFilmState.value.copy(text = event.value)
            }
            is AdminScreenEvent.FilmsSearchFocusChanged -> {
                _searchFilmState.value = searchFilmState.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            searchFilmState.value.text.isBlank()
                )
            }
            is AdminScreenEvent.DeleteFilmClicked -> {
                deleteFilm(event.title)
            }
            AdminScreenEvent.AddFilmClicked -> {
                _isAddFilmsDialogVisible.value = true
            }
            is AdminScreenEvent.FilmCategoriesFocusChanged -> {
                _filmCategoriesState.value = filmCategoriesState.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            filmCategoriesState.value.text.isBlank()
                )
            }
            is AdminScreenEvent.FilmCategoriesEntered -> {
                _filmCategoriesState.value = filmCategoriesState.value.copy(text = event.value)
            }
            is AdminScreenEvent.FilmDescriptionFocusChanged -> {
                _filmDescriptionState.value = filmDescriptionState.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            filmDescriptionState.value.text.isBlank()
                )
            }
            is AdminScreenEvent.FilmDescriptionEntered -> {
                _filmDescriptionState.value = filmDescriptionState.value.copy(text = event.value)
            }
            is AdminScreenEvent.FilmTitleFocusChanged -> {
                _filmTitleState.value = filmTitleState.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            filmTitleState.value.text.isBlank()
                )
            }
            is AdminScreenEvent.FilmTitleEntered -> {
                _filmTitleState.value = filmTitleState.value.copy(text = event.value)
            }
            is AdminScreenEvent.FilmUrlFocusChanged -> {
                _filmUrlState.value = filmUrlState.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            filmUrlState.value.text.isBlank()
                )
            }
            is AdminScreenEvent.FilmUrlEntered -> {
                _filmUrlState.value = filmUrlState.value.copy(text = event.value)
            }
            is AdminScreenEvent.FilmImagesEntered -> {
                _filmImageUrlsState.value = filmImageUrlsState.value.copy(text = event.value)
            }
            is AdminScreenEvent.FilmImagesFocusChanged -> {
                _filmImageUrlsState.value = filmImageUrlsState.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            filmImageUrlsState.value.text.isBlank()
                )
            }
            AdminScreenEvent.SaveFilm -> {
                addFilm(
                    Film(
                        name = filmTitleState.value.text,
                        imageUrls = filmImageUrlsState.value.text.split(";"),
                        description = filmDescriptionState.value.text,
                        genres = filmCategoriesState.value.text.split(";"),
                        likedBy = emptyList(),
                        dislikedBy = emptyList(),
                        url = filmUrlState.value.text
                    )
                )
            }
        }
    }

    fun loadData() {
        getWatchTimeStates()
        getUsersCount()
        getFilmsCount()
        getAllUsers()
        getAllFilms()
    }

    private fun addFilm(film: Film) {
        addFilmsUseCase(film).onEach {
            if (it is Resource.Success) {
                getFilmsCount()
                getAllFilms()
            }
        }.launchIn(viewModelScope)
    }

    private fun getAllFilms() {
        getAllFilmsUseCase().onEach { result ->
            if (result is Resource.Error) {
                _error.value = result.message
                    ?: resource.getString(R.string.unknown_error_occurred)

            } else if (result is Resource.Success) {
                _error.value = ""
                _films.clear()
                _films.addAll(result.data ?: emptyList())
            }
        }.launchIn(viewModelScope)
    }

    private fun deleteUser(email: String) {
        deleteUserUseCase(email).onEach { result ->
            if (result is Resource.Error) {
                _error.value = result.message
                    ?: resource.getString(R.string.unknown_error_occurred)

            } else if (result is Resource.Success) {
                _error.value = ""
                getUsersCount()
                getAllUsers()
            }
        }.launchIn(viewModelScope)
    }

    private fun deleteFilm(filmTitle: String) {
        deleteFilmUseCase(filmTitle).onEach {
            if (it is Resource.Success) {
                _error.value = ""
                getFilmsCount()
                getAllFilms()
            }
        }.launchIn(viewModelScope)
    }

    private fun getAllUsers() {
        getAllUsersUseCase().onEach { result ->
            if (result is Resource.Error) {
                _error.value = result.message
                    ?: resource.getString(R.string.unknown_error_occurred)

            } else if (result is Resource.Success) {
                _error.value = ""
                _users.clear()
                _users.addAll(
                    if (result.data?.isNotEmpty() == true) {
                        result.data
                    } else emptyList()
                )
            }
        }.launchIn(viewModelScope)
    }

    private fun getWatchTimeStates() {
        getTotalWatchTimeAdminUseCase().onEach { result ->
            if (result is Resource.Error) {
                _error.value = result.message
                    ?: resource.getString(R.string.unknown_error_occurred)

            } else if (result is Resource.Success) {
                _error.value = ""
                _watchTimeStats.clear()
                _watchTimeStats.addAll(
                    if (result.data?.isNotEmpty() == true) {
                        result.data
                    } else emptyList()
                )
            }
        }.launchIn(viewModelScope)
    }

    private fun getUsersCount() {
        getUsersCountUseCase().onEach { result ->
            if (result is Resource.Success) {
                _error.value = ""
                _userCount.value = result.data ?: 0
            }
        }.launchIn(viewModelScope)
    }

    private fun getFilmsCount() {
        getFilmsCountUseCase().onEach { result ->
            if (result is Resource.Success) {
                _error.value = ""
                _filmCount.value = result.data ?: 0
            }
        }.launchIn(viewModelScope)
    }
}