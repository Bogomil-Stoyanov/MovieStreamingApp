package eu.bbsapps.forgottenfilmsapp.presentation.main.my_list_screen

import com.google.common.truth.Truth.assertThat
import eu.bbsapps.forgottenfilmsapp.data.repoistory.FakeFilmRepository
import eu.bbsapps.forgottenfilmsapp.domain.use_case.account_management.GetNicknameUseCase
import eu.bbsapps.forgottenfilmsapp.domain.use_case.film.movielist.GetFilmListUseCase
import eu.bbsapps.forgottenfilmsapp.util.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MyListViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: MyListViewModel

    @Before
    fun setup() {
        viewModel = MyListViewModel(
            GetFilmListUseCase(FakeFilmRepository()),
            GetNicknameUseCase(FakeFilmRepository())
        )
    }

    @Test
    fun getNicknameEqualsNickname() {
        viewModel.getNickname()
        assertThat(viewModel.nickname.value).isEqualTo("Nickname")
    }

    @Test
    fun getMovieListNotEmpty() {
        viewModel.getMoviesList()
        assertThat(viewModel.state.value.filmList).isNotEmpty()
    }
}