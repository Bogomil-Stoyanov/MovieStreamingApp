package eu.bbsapps.forgottenfilmsapp.presentation.main.more_screen

import android.content.Context
import android.content.SharedPreferences
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import eu.bbsapps.forgottenfilmsapp.common.Constants
import eu.bbsapps.forgottenfilmsapp.data.repoistory.FakeFilmRepository
import eu.bbsapps.forgottenfilmsapp.domain.use_case.account_management.*
import eu.bbsapps.forgottenfilmsapp.domain.use_case.admin.IsAdminUseCase
import eu.bbsapps.forgottenfilmsapp.domain.use_case.film.GetAllGenresUseCase
import eu.bbsapps.forgottenfilmsapp.util.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MoreViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var context: Context
    private lateinit var viewModel: MoreViewModel
    private lateinit var sharedPrefs: SharedPreferences

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        sharedPrefs = context.getSharedPreferences(
            Constants.ENCRYPTED_SHARED_PREF_NAME,
            Context.MODE_PRIVATE
        )
        viewModel = MoreViewModel(
            GetNicknameUseCase(FakeFilmRepository()),
            ChangeNicknameUseCase(FakeFilmRepository()),
            UpdateGenresUseCase(FakeFilmRepository()),
            GetGenresUseCase(FakeFilmRepository()),
            GetAllGenresUseCase(FakeFilmRepository()),
            GetWatchTimeUseCase(FakeFilmRepository()),
            IsAdminUseCase(FakeFilmRepository()),
            sharedPrefs
        )
    }

    @Test
    fun changeNicknameDialogSetVisible() {
        viewModel.onEvent(MoreScreenEvent.ChangeNicknameClicked)
        assertThat(viewModel.isChangeNicknameDialogVisible.value).isTrue()
    }

    @Test
    fun changeNicknameDialogDismissed() {
        viewModel.onEvent(MoreScreenEvent.ChangeNicknameClicked)
        assertThat(viewModel.isChangeNicknameDialogVisible.value).isTrue()
        viewModel.onEvent(MoreScreenEvent.ChangeNicknameDialogCanceled)
        assertThat(viewModel.isChangeNicknameDialogVisible.value).isFalse()
    }

    @Test
    fun changeFavouriteCategoriesDialogSetVisible() {
        viewModel.onEvent(MoreScreenEvent.FavouriteCategoriesClicked)
        assertThat(viewModel.isFavouriteCategoriesDialogVisible.value).isTrue()
    }

    @Test
    fun changeFavouriteCategoriesDialogDismissed() {
        viewModel.onEvent(MoreScreenEvent.FavouriteCategoriesClicked)
        assertThat(viewModel.isFavouriteCategoriesDialogVisible.value).isTrue()
        viewModel.onEvent(MoreScreenEvent.FavouriteCategoriesDialogCanceled)
        assertThat(viewModel.isFavouriteCategoriesDialogVisible.value).isFalse()
    }

    @Test
    fun enteredValidNicknameNoError() {
        viewModel.onEvent(MoreScreenEvent.EnteredNickname("Nick"))
        assertThat(viewModel.changeNicknameState.value.error).isEqualTo("")
    }

    @Test
    fun enteredValidNicknameError() {
        viewModel.onEvent(MoreScreenEvent.EnteredNickname(""))
        assertThat(viewModel.changeNicknameState.value.error).isNotEqualTo("")
    }

    @Test
    fun addNewInterest() {
        viewModel.onEvent(MoreScreenEvent.InterestChangedInDialog("Action"))
        assertThat(viewModel.userGenres.value).contains("Action")
    }

    @Test
    fun removeExistingInterest() {
        viewModel.onEvent(MoreScreenEvent.InterestChangedInDialog("Action"))
        viewModel.onEvent(MoreScreenEvent.InterestChangedInDialog("Action"))
        assertThat(viewModel.userGenres.value).doesNotContain("Action")
    }

    @Test
    fun getNicknameSuccessful() {
        viewModel.getNickname()
        assertThat(viewModel.nickname.value).isEqualTo("Nickname")
    }
}