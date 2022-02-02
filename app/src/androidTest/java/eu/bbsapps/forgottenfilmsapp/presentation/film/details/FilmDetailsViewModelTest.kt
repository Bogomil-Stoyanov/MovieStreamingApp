package eu.bbsapps.filmstreamingapp.presentation.film.details

import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth.assertThat
import eu.bbsapps.forgottenfilmsapp.data.repoistory.FakeFilmRepository
import eu.bbsapps.forgottenfilmsapp.domain.use_case.film.FilmDetailsUseCase
import eu.bbsapps.forgottenfilmsapp.domain.use_case.film.movielist.AddFilmToListUseCase
import eu.bbsapps.forgottenfilmsapp.domain.use_case.film.movielist.FilmListUseCases
import eu.bbsapps.forgottenfilmsapp.domain.use_case.film.movielist.IsFilmAddedToListUseCase
import eu.bbsapps.forgottenfilmsapp.domain.use_case.film.movielist.RemoveFilmFromListUseCase
import eu.bbsapps.forgottenfilmsapp.domain.use_case.film.rating.*
import eu.bbsapps.forgottenfilmsapp.presentation.film.details.FilmDetailsEvent
import eu.bbsapps.forgottenfilmsapp.presentation.film.details.FilmDetailsViewModel
import eu.bbsapps.forgottenfilmsapp.util.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class FilmDetailsViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: FilmDetailsViewModel
    private lateinit var savedStateHandle: SavedStateHandle

    @Before
    fun setup() {
        savedStateHandle = SavedStateHandle().apply {
            set("filmId", "id")
        }
        viewModel = FilmDetailsViewModel(
            FilmDetailsUseCase(FakeFilmRepository()),
            FilmRatingUseCases(
                FilmLikedUseCase(FakeFilmRepository()),
                FilmDislikedUseCase(FakeFilmRepository()),
                FilmLikesCountUseCase(FakeFilmRepository()),
                FilmDislikesCountUseCase(FakeFilmRepository())
            ),
            FilmListUseCases(
                AddFilmToListUseCase(FakeFilmRepository()),
                RemoveFilmFromListUseCase(FakeFilmRepository()),
                IsFilmAddedToListUseCase(FakeFilmRepository())
            ),
            savedStateHandle
        )
    }

    @Test
    fun checkSavedStateHandleId() {
        assertThat(viewModel.id).isEqualTo("id")
    }

    @Test
    fun likeClickedExpected1() {
        viewModel.onEvent(FilmDetailsEvent.LikeClicked)
        assertThat(viewModel.state.value.film?.isLiked).isEqualTo(1)
    }

    @Test
    fun dislikeClickedExpectedMinus1() {
        viewModel.onEvent(FilmDetailsEvent.DislikeClicked)
        assertThat(viewModel.state.value.film?.isLiked).isEqualTo(-1)
    }

    @Test
    fun addFilmToList() {
        viewModel.onEvent(FilmDetailsEvent.AddToList)
        assertThat(viewModel.isFilmAddedToList.value).isTrue()
    }

    @Test
    fun filmDetailsNotNull() {
        assertThat(viewModel.state.value.film).isNotNull()
    }
}
