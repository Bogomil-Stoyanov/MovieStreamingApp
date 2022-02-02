package eu.bbsapps.forgottenfilmsapp.presentation.auth.login

import android.content.Context
import android.content.SharedPreferences
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import eu.bbsapps.forgottenfilmsapp.common.Constants
import eu.bbsapps.forgottenfilmsapp.data.remote.BasicAuthInterceptor
import eu.bbsapps.forgottenfilmsapp.data.repoistory.FakeFilmRepository
import eu.bbsapps.forgottenfilmsapp.domain.use_case.auth.LoginUseCase
import eu.bbsapps.forgottenfilmsapp.util.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class LoginViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var context: Context
    private lateinit var viewModel: LoginViewModel
    private lateinit var sharedPrefs: SharedPreferences

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        sharedPrefs = context.getSharedPreferences(
            Constants.ENCRYPTED_SHARED_PREF_NAME,
            Context.MODE_PRIVATE
        )
        viewModel = LoginViewModel(
            LoginUseCase(FakeFilmRepository()), sharedPrefs, BasicAuthInterceptor()
        )
    }

    @Test
    fun enteredEmail() {
        viewModel.onEvent(LoginEvent.EnteredEmail("email@abc.com"))
        assertThat(viewModel.email.value.text).isEqualTo("email@abc.com")
    }

    @Test
    fun enteredPassword() {
        viewModel.onEvent(LoginEvent.EnteredPassword("Valid_123"))
        assertThat(viewModel.password.value.text).isEqualTo("Valid_123")
    }

    @Test
    fun rememberMeClickedInitiallyFalse() {
        viewModel.onEvent(LoginEvent.RememberMeClicked)
        assertThat(viewModel.rememberMe.value).isEqualTo(true)
    }

    @Test
    fun rememberMeClickedInitiallyTrue() {
        viewModel.onEvent(LoginEvent.RememberMeClicked)
        viewModel.onEvent(LoginEvent.RememberMeClicked)
        assertThat(viewModel.rememberMe.value).isEqualTo(false)
    }

    @Test
    fun changePasswordVisibilityClicked() {
        viewModel.onEvent(LoginEvent.ChangePasswordVisibility(false))
        assertThat(viewModel.password.value.isPasswordVisible).isEqualTo(false)
    }

    @Test
    fun loginSuccessful() {
        viewModel.onEvent(LoginEvent.EnteredEmail("email@abc.com"))
        viewModel.onEvent(LoginEvent.EnteredPassword("Password_123"))
        viewModel.onEvent(LoginEvent.LoginClicked)
        assertThat(viewModel.state.value.loginSuccessful).isTrue()
    }

    @Test
    fun rememberMeSelectedLoginSuccessful() {
        viewModel.onEvent(LoginEvent.EnteredEmail("email@abc.com"))
        viewModel.onEvent(LoginEvent.EnteredPassword("Password_123"))
        viewModel.onEvent(LoginEvent.LoginClicked)
        viewModel.onEvent(LoginEvent.RememberMeClicked)
        assertThat(viewModel.state.value.loginSuccessful).isTrue()
    }
}