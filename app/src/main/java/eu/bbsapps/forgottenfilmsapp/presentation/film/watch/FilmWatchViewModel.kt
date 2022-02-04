package eu.bbsapps.forgottenfilmsapp.presentation.film.watch

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.exoplayer2.SimpleExoPlayer
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.bbsapps.forgottenfilmsapp.data.remote.dto.requests.user.management.GenreWatchTimeRequest
import eu.bbsapps.forgottenfilmsapp.domain.use_case.account_management.AddWatchTimeUseCase
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject

@HiltViewModel
class FilmWatchViewModel @Inject constructor(
    private val addWatchTimeUseCase: AddWatchTimeUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val movieUrl: String = savedStateHandle.get("filmUrl") ?: ""
    val genre: String = savedStateHandle.get("filmGenre") ?: ""
    var exoPlayer: SimpleExoPlayer? = null
    private var timeWatchedInSeconds = 0

    fun elapsedSecondWatching() {
        timeWatchedInSeconds++
    }

    fun uploadWatchTime() {
        addWatchTimeUseCase(
            GenreWatchTimeRequest(
                genre,
                timeWatchedInSeconds
            )
        ).launchIn(viewModelScope).invokeOnCompletion { timeWatchedInSeconds = 0 }
    }
}