package eu.bbsapps.forgottenfilmsapp.presentation.main.my_list_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.bbsapps.forgottenfilmsapp.common.Resource
import eu.bbsapps.forgottenfilmsapp.domain.use_case.account_management.GetNicknameUseCase
import eu.bbsapps.forgottenfilmsapp.domain.use_case.film.movielist.GetFilmListUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MyListViewModel @Inject constructor(
    private val getFilmListUseCase: GetFilmListUseCase,
    private val getNicknameUseCase: GetNicknameUseCase
) : ViewModel() {

    private val _state = mutableStateOf(MyListState())
    val state: State<MyListState> = _state

    private val _nickname = mutableStateOf("")
    val nickname: State<String> = _nickname

    fun getMoviesList() {
        getFilmListUseCase().onEach { result ->
            when (result) {
                is Resource.Error -> {
                    _state.value =
                        MyListState(error = result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    _state.value = MyListState(isLoading = true)
                }
                is Resource.Success -> {
                    _state.value = MyListState(filmList = result.data ?: emptyList())
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getNickname() {
        getNicknameUseCase().onEach { result ->
            if (result is Resource.Success) {
                _nickname.value = result.data?.message ?: ""
            }
        }.launchIn(viewModelScope)
    }
}