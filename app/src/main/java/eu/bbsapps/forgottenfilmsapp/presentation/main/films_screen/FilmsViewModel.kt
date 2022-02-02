package eu.bbsapps.forgottenfilmsapp.presentation.main.films_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.bbsapps.forgottenfilmsapp.common.Resource
import eu.bbsapps.forgottenfilmsapp.domain.use_case.film.GetAllFilmsUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class FilmsViewModel @Inject constructor(
    private val getAllFilmsUseCase: GetAllFilmsUseCase
) : ViewModel() {

    private val _state = mutableStateOf(FilmState())
    val state: State<FilmState> = _state

    init {
        getAllMovies()
    }

    private fun getAllMovies() {
        getAllFilmsUseCase().onEach {
            when (it) {
                is Resource.Error -> {
                    _state.value = FilmState(
                        error = it.message ?: "An unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    _state.value = FilmState(
                        isLoading = true
                    )
                }
                is Resource.Success -> {
                    _state.value = FilmState(
                        film = it.data
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}