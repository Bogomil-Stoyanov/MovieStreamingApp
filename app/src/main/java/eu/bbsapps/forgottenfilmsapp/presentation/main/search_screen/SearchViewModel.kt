package eu.bbsapps.forgottenfilmsapp.presentation.main.search_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.bbsapps.forgottenfilmsapp.ForgottenFilmsApp.Companion.resource
import eu.bbsapps.forgottenfilmsapp.R
import eu.bbsapps.forgottenfilmsapp.common.Resource
import eu.bbsapps.forgottenfilmsapp.domain.use_case.film.SearchFilmsUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchFilmsUseCase: SearchFilmsUseCase,
) : ViewModel() {

    private val _state = mutableStateOf(SearchState())
    val state: State<SearchState> = _state

    private val _query = mutableStateOf("")
    val query: State<String> = _query

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.EnteredQuery -> {
                _query.value = event.query
                _state.value = state.value.copy(hasSearched = true)
                searchMovies()
            }
            SearchEvent.SearchClicked -> {
                _state.value = state.value.copy(hasSearched = true)
                searchMovies()
            }
        }
    }

    private fun searchMovies() {
        searchFilmsUseCase(query.value).onEach { result ->
            when (result) {
                is Resource.Error -> {
                    _state.value =
                        SearchState(
                            error = result.message
                                ?: resource.getString(R.string.unknown_error_occurred),
                            hasSearched = true
                        )
                }
                is Resource.Loading -> {
                    _state.value = SearchState(isLoading = true, hasSearched = true)
                }
                is Resource.Success -> {
                    _state.value =
                        SearchState(films = result.data ?: emptyList(), hasSearched = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}