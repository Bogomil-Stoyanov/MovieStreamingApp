package eu.bbsapps.forgottenfilmsapp.presentation.film.details

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.bbsapps.forgottenfilmsapp.common.Resource
import eu.bbsapps.forgottenfilmsapp.domain.use_case.film.FilmDetailsUseCase
import eu.bbsapps.forgottenfilmsapp.domain.use_case.film.movielist.FilmListUseCases
import eu.bbsapps.forgottenfilmsapp.domain.use_case.film.rating.FilmRatingUseCases
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class FilmDetailsViewModel @Inject constructor(
    private val filmDetailsUseCase: FilmDetailsUseCase,
    private val filmRatingUseCases: FilmRatingUseCases,
    private val filmListUseCases: FilmListUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(FilmDetailsState())
    val state: State<FilmDetailsState> = _state

    private val _isFilmAddedToList = mutableStateOf(false)
    val isFilmAddedToList: State<Boolean> = _isFilmAddedToList

    private val _isGalleryVisible = mutableStateOf(false)
    val isGalleryVisible: State<Boolean> = _isGalleryVisible

    private val _currentImageUrl = mutableStateOf<String>("")
    val currentImageUrl: State<String> = _currentImageUrl

    var id: String = ""

    init {
        id = savedStateHandle.get("filmId") ?: ""
        getFilmDetails(id)
        isFilmAddedToList(id)
    }

    fun onEvent(event: FilmDetailsEvent) {
        when (event) {
            FilmDetailsEvent.AddToList -> {
                if (isFilmAddedToList.value) {
                    removeFilmFromList(id)
                } else {
                    addFilmToList(id)
                }
            }
            FilmDetailsEvent.DislikeClicked -> {
                dislikeFilm(id)
            }
            FilmDetailsEvent.LikeClicked -> {
                likeFilm(id)
            }
            is FilmDetailsEvent.ClickedImage -> {
                _isGalleryVisible.value = true
                _currentImageUrl.value = event.imageUrl
            }
            FilmDetailsEvent.GalleryClosed -> {
                _isGalleryVisible.value = false
                _currentImageUrl.value = ""
            }
            FilmDetailsEvent.NextImageClicked -> {
                val currentIndex = state.value.film?.imageUrls?.indexOf(currentImageUrl.value) ?: 0
                val nextIndex =
                    if (currentIndex == (state.value.film?.imageUrls?.size
                            ?: 0) - 1
                    ) 0 else currentIndex + 1
                _currentImageUrl.value = state.value.film?.imageUrls?.get(nextIndex) ?: ""
            }
            FilmDetailsEvent.PreviousImageClicked -> {
                val currentIndex = state.value.film?.imageUrls?.indexOf(currentImageUrl.value) ?: 0
                val previousIndex =
                    if (currentIndex == 0) (state.value.film?.imageUrls?.size ?: 0) - 1
                    else currentIndex - 1
                _currentImageUrl.value = state.value.film?.imageUrls?.get(previousIndex) ?: ""
            }
        }
    }

    private fun likeFilm(id: String) {
        filmRatingUseCases.filmLikedUseCase(id).onEach {
            if (it is Resource.Success) {
                _state.value = FilmDetailsState(
                    film = state.value.film?.copy(
                        isLiked = it.data ?: 0
                    )
                )
                getDislikeCount(id)
                getLikeCount(id)
            }
        }.launchIn(viewModelScope)
    }

    private fun dislikeFilm(id: String) {
        filmRatingUseCases.filmDislikedUseCase(id)
            .onEach {
                if (it is Resource.Success) {
                    _state.value = FilmDetailsState(
                        film = state.value.film?.copy(
                            isLiked = it.data ?: 0
                        )
                    )
                    getDislikeCount(id)
                    getLikeCount(id)
                }
            }.launchIn(viewModelScope)
    }

    private fun getLikeCount(id: String) {
        filmRatingUseCases.filmLikesCountUseCase(id)
            .onEach {
                if (it is Resource.Success) {
                    _state.value = FilmDetailsState(
                        film = state.value.film?.copy(
                            likes = it.data ?: 0
                        )
                    )
                }
            }.launchIn(viewModelScope)
    }

    private fun getDislikeCount(id: String) {
        filmRatingUseCases.filmDislikesCountUseCase(id)
            .onEach {
                if (it is Resource.Success) {
                    _state.value = FilmDetailsState(
                        film = state.value.film?.copy(
                            dislikes = it.data ?: 0
                        )
                    )
                }
            }.launchIn(viewModelScope)
    }

    private fun getFilmDetails(id: String) {
        filmDetailsUseCase(id).onEach { result ->
            when (result) {
                is Resource.Error -> {
                    _state.value =
                        FilmDetailsState(error = result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    _state.value = FilmDetailsState(isLoading = true)
                }
                is Resource.Success -> {
                    _state.value = FilmDetailsState(film = result.data)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun isFilmAddedToList(id: String) {
        filmListUseCases.isFilmAddedToListUseCase(id).onEach { result ->
            if (result is Resource.Success) {
                _isFilmAddedToList.value = result.data ?: false
            }
        }.launchIn(viewModelScope)
    }

    private fun addFilmToList(id: String) {
        filmListUseCases.addFilmToListUseCase(id).onEach { result ->
            if (result is Resource.Success) {
                _isFilmAddedToList.value = true
            }
        }.launchIn(viewModelScope)
    }

    private fun removeFilmFromList(id: String) {
        filmListUseCases.removeFilmFromListUseCase(id).onEach { result ->
            if (result is Resource.Success) {
                _isFilmAddedToList.value = false
            }
        }.launchIn(viewModelScope)
    }
}