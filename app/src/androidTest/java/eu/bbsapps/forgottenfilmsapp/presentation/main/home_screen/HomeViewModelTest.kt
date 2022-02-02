package eu.bbsapps.forgottenfilmsapp.presentation.main.home_screen

import com.google.common.truth.Truth.assertThat
import eu.bbsapps.forgottenfilmsapp.data.repoistory.FakeFilmRepository
import eu.bbsapps.forgottenfilmsapp.domain.use_case.feed.GetFeedUseCase
import eu.bbsapps.forgottenfilmsapp.util.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        viewModel = HomeViewModel(GetFeedUseCase(FakeFilmRepository()))
    }

    @Test
    fun getFeedSuccessful() {
        viewModel.getFeed()
        assertThat(viewModel.state.value.movie).isNotEmpty()
    }
}