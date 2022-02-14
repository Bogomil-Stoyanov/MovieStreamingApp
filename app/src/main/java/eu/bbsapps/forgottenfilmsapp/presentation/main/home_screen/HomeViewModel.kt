package eu.bbsapps.forgottenfilmsapp.presentation.main.home_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.bbsapps.forgottenfilmsapp.ForgottenFilmsApp.Companion.resource
import eu.bbsapps.forgottenfilmsapp.R
import eu.bbsapps.forgottenfilmsapp.common.Resource
import eu.bbsapps.forgottenfilmsapp.domain.use_case.feed.GetFeedUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getFeedUseCase: GetFeedUseCase
) : ViewModel() {

    private val _state = mutableStateOf(HomeScreenState())
    val state: State<HomeScreenState> = _state

    init {
        getFeed()
    }

    fun getFeed() {
        getFeedUseCase().onEach { result ->
            when (result) {
                is Resource.Error -> {
                    _state.value =
                        HomeScreenState(
                            error = result.message
                                ?: resource.getString(R.string.unknown_error_occurred)
                        )
                }
                is Resource.Loading -> {
                    _state.value = HomeScreenState(isLoading = true)
                }
                is Resource.Success -> {
                    _state.value = HomeScreenState(movie = result.data)
                }
            }
        }.launchIn(viewModelScope)
    }
}