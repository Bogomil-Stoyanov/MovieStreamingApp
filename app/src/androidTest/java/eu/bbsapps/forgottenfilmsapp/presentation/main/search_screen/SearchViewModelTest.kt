package eu.bbsapps.forgottenfilmsapp.presentation.main.search_screen

import com.google.common.truth.Truth.assertThat
import eu.bbsapps.forgottenfilmsapp.data.repoistory.FakeFilmRepository
import eu.bbsapps.forgottenfilmsapp.domain.use_case.film.SearchFilmsUseCase
import eu.bbsapps.forgottenfilmsapp.util.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SearchViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: SearchViewModel

    @Before
    fun setup() {
        viewModel = SearchViewModel(
            SearchFilmsUseCase(FakeFilmRepository())
        )
    }

    @Test
    fun enteredSearchQuery() {
        viewModel.onEvent(SearchEvent.EnteredQuery("Movie"))
        assertThat(viewModel.query.value).isEqualTo("Movie")
    }

    @Test
    fun searchMoviesNotEmpty() {
        viewModel.onEvent(SearchEvent.SearchClicked)
        assertThat(viewModel.state.value.films).isNotEmpty()
    }

}