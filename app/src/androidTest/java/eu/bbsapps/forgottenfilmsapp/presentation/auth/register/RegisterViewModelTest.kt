package eu.bbsapps.forgottenfilmsapp.presentation.auth.register

import com.google.common.truth.Truth.assertThat
import eu.bbsapps.forgottenfilmsapp.data.repoistory.FakeFilmRepository
import eu.bbsapps.forgottenfilmsapp.domain.use_case.auth.RegisterUseCase
import eu.bbsapps.forgottenfilmsapp.domain.use_case.film.GetAllGenresUseCase
import eu.bbsapps.forgottenfilmsapp.util.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class RegisterViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: RegisterViewModel

    @Before
    fun setup() {
        viewModel = RegisterViewModel(
            RegisterUseCase(FakeFilmRepository()),
            GetAllGenresUseCase(FakeFilmRepository())
        )
    }

    @Test
    fun validEmailNoError() {
        viewModel.onEvent(RegisterEvent.EnteredEmail("valid@email.com"))
        assertThat(viewModel.email.value.error).isEqualTo("")
    }

    @Test
    fun invalidEmailError() {
        viewModel.onEvent(RegisterEvent.EnteredEmail("invalidemail.com"))
        assertThat(viewModel.email.value.error).isNotEqualTo("")
    }

    @Test
    fun validPasswordNoError() {
        viewModel.onEvent(RegisterEvent.EnteredPassword("Password_123"))
        assertThat(viewModel.password.value.error).isEqualTo("")
    }

    @Test
    fun invalidPasswordError() {
        viewModel.onEvent(RegisterEvent.EnteredPassword("password_123"))
        assertThat(viewModel.password.value.error).isNotEqualTo("")
    }

    @Test
    fun validConfirmPasswordNoError() {
        viewModel.onEvent(RegisterEvent.EnteredPassword("Password_123"))
        viewModel.onEvent(RegisterEvent.EnteredConfirmPassword("Password_123"))
        assertThat(viewModel.confirmPassword.value.error).isEqualTo("")
    }

    @Test
    fun invalidConfirmPasswordError() {
        viewModel.onEvent(RegisterEvent.EnteredConfirmPassword("password_123"))
        assertThat(viewModel.confirmPassword.value.error).isNotEqualTo("")
    }

    @Test
    fun validNicknameNoError() {
        viewModel.onEvent(RegisterEvent.EnteredNickname("Nickname"))
        assertThat(viewModel.nickname.value.error).isEqualTo("")
    }

    @Test
    fun invalidNicknameError() {
        viewModel.onEvent(RegisterEvent.EnteredNickname("   "))
        assertThat(viewModel.nickname.value.error).isNotEqualTo("")
    }

    @Test
    fun addedInterest() {
        viewModel.onEvent(RegisterEvent.ChangedInterest("Action"))
        assertThat(viewModel.selectedInterests).contains("Action")
    }

    @Test
    fun changePasswordVisibilityClicked() {
        viewModel.onEvent(RegisterEvent.ChangePasswordVisibility(false))
        assertThat(viewModel.password.value.isPasswordVisible).isEqualTo(false)
    }

    @Test
    fun changeConfirmPasswordVisibilityClicked() {
        viewModel.onEvent(RegisterEvent.ChangeConfirmPasswordVisibility(false))
        assertThat(viewModel.confirmPassword.value.isPasswordVisible).isEqualTo(false)
    }

    @Test
    fun registerSuccessful() {
        viewModel.onEvent(RegisterEvent.EnteredEmail("email@abc.com"))
        viewModel.onEvent(RegisterEvent.EnteredPassword("Password_123"))
        viewModel.onEvent(RegisterEvent.EnteredConfirmPassword("Password_123"))
        viewModel.onEvent(RegisterEvent.EnteredNickname("Nickname"))
        viewModel.onEvent(RegisterEvent.ChangedInterest("Action"))
        viewModel.onEvent(RegisterEvent.ChangedInterest("Comedy"))
        viewModel.onEvent(RegisterEvent.ChangedInterest("Documentary"))
        viewModel.onEvent(RegisterEvent.PrivacyPolicyAgreeClicked)
        viewModel.onEvent(RegisterEvent.RegisterClicked)
        assertThat(viewModel.state.value.registerSuccessful).isTrue()
    }

    @Test
    fun registerUnsuccessfulNotEnoughInterests() {
        viewModel.onEvent(RegisterEvent.EnteredEmail("email@abc.com"))
        viewModel.onEvent(RegisterEvent.EnteredPassword("Password_123"))
        viewModel.onEvent(RegisterEvent.EnteredConfirmPassword("Password_123"))
        viewModel.onEvent(RegisterEvent.EnteredNickname("Nickname"))
        viewModel.onEvent(RegisterEvent.ChangedInterest("Action"))
        viewModel.onEvent(RegisterEvent.ChangedInterest("Comedy"))
        viewModel.onEvent(RegisterEvent.RegisterClicked)
        assertThat(viewModel.state.value.registerSuccessful).isNull()
    }

    @Test
    fun registerUnsuccessfulInvalidEmail() {
        viewModel.onEvent(RegisterEvent.EnteredEmail("emailabc.com"))
        viewModel.onEvent(RegisterEvent.EnteredPassword("Password_123"))
        viewModel.onEvent(RegisterEvent.EnteredConfirmPassword("Password_123"))
        viewModel.onEvent(RegisterEvent.EnteredNickname("Nickname"))
        viewModel.onEvent(RegisterEvent.ChangedInterest("Action"))
        viewModel.onEvent(RegisterEvent.ChangedInterest("Comedy"))
        viewModel.onEvent(RegisterEvent.ChangedInterest("Documentary"))
        viewModel.onEvent(RegisterEvent.RegisterClicked)
        assertThat(viewModel.state.value.registerSuccessful).isNull()
    }


    @Test
    fun registerUnsuccessfulInvalidPassword() {
        viewModel.onEvent(RegisterEvent.EnteredEmail("email@abc.com"))
        viewModel.onEvent(RegisterEvent.EnteredPassword("Password123"))
        viewModel.onEvent(RegisterEvent.EnteredConfirmPassword("Password_123"))
        viewModel.onEvent(RegisterEvent.EnteredNickname("Nickname"))
        viewModel.onEvent(RegisterEvent.ChangedInterest("Action"))
        viewModel.onEvent(RegisterEvent.ChangedInterest("Comedy"))
        viewModel.onEvent(RegisterEvent.ChangedInterest("Documentary"))
        viewModel.onEvent(RegisterEvent.RegisterClicked)
        assertThat(viewModel.state.value.registerSuccessful).isNull()
    }

    @Test
    fun registerUnsuccessfulConfirmInvalidPassword() {
        viewModel.onEvent(RegisterEvent.EnteredEmail("email@abc.com"))
        viewModel.onEvent(RegisterEvent.EnteredPassword("Password_123"))
        viewModel.onEvent(RegisterEvent.EnteredConfirmPassword("differentPassword"))
        viewModel.onEvent(RegisterEvent.EnteredNickname("Nickname"))
        viewModel.onEvent(RegisterEvent.ChangedInterest("Comedy"))
        viewModel.onEvent(RegisterEvent.ChangedInterest("Action"))
        viewModel.onEvent(RegisterEvent.ChangedInterest("Horror"))
        viewModel.onEvent(RegisterEvent.RegisterClicked)
        assertThat(viewModel.state.value.registerSuccessful).isNull()
    }

    @Test
    fun registerUnsuccessfulInvalidNickname() {
        viewModel.onEvent(RegisterEvent.EnteredEmail("email@abc.com"))
        viewModel.onEvent(RegisterEvent.EnteredPassword("Password_123"))
        viewModel.onEvent(RegisterEvent.EnteredConfirmPassword("Password_123"))
        viewModel.onEvent(RegisterEvent.EnteredNickname(""))
        viewModel.onEvent(RegisterEvent.ChangedInterest("Action"))
        viewModel.onEvent(RegisterEvent.ChangedInterest("Comedy"))
        viewModel.onEvent(RegisterEvent.ChangedInterest("Horror"))
        viewModel.onEvent(RegisterEvent.RegisterClicked)
        assertThat(viewModel.state.value.registerSuccessful).isNull()
    }
}