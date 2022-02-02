package eu.bbsapps.forgottenfilmsapp.presentation.auth.register

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.NavController
import com.google.common.truth.Truth.assertThat
import eu.bbsapps.forgottenfilmsapp.common.TestTags.CONFIRM_PASSWORD_TEXT_FIELD
import eu.bbsapps.forgottenfilmsapp.common.TestTags.EMAIL_TEXT_FIELD
import eu.bbsapps.forgottenfilmsapp.common.TestTags.NICKNAME_TEXT_FIELD
import eu.bbsapps.forgottenfilmsapp.common.TestTags.PASSWORD_TEXT_FIELD
import eu.bbsapps.forgottenfilmsapp.common.TestTags.REGISTER_BUTTON
import eu.bbsapps.forgottenfilmsapp.data.repoistory.FakeFilmRepository
import eu.bbsapps.forgottenfilmsapp.data.repoistory.genres
import eu.bbsapps.forgottenfilmsapp.domain.use_case.auth.RegisterUseCase
import eu.bbsapps.forgottenfilmsapp.domain.use_case.film.GetAllGenresUseCase
import eu.bbsapps.forgottenfilmsapp.presentation.MainActivity
import eu.bbsapps.forgottenfilmsapp.presentation.ui.theme.ForgottenFilmsAppTheme
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RegisterScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule(MainActivity::class.java)

    @RelaxedMockK
    lateinit var navController: NavController

    private lateinit var viewModel: RegisterViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = RegisterViewModel(
            RegisterUseCase(FakeFilmRepository()),
            GetAllGenresUseCase(FakeFilmRepository())
        )
        composeTestRule.setContent {
            ForgottenFilmsAppTheme {
                RegisterScreen(
                    navController = navController,
                    viewModel = viewModel
                )
            }
        }
    }

    @Test
    fun inputEmail() {
        composeTestRule.onNodeWithTag(EMAIL_TEXT_FIELD).performTextInput("emial@mail.com")
        assertThat(viewModel.email.value.text).isEqualTo("emial@mail.com")
    }

    @Test
    fun inputInvalidEmail_error() {
        composeTestRule.onNodeWithTag(EMAIL_TEXT_FIELD).performTextInput("emialmail.com")
        assertThat(viewModel.email.value.error).isNotEqualTo("")
    }

    @Test
    fun inputPassword() {
        composeTestRule.onNodeWithTag(PASSWORD_TEXT_FIELD)
            .performTextInput("Password_123")
        assertThat(viewModel.password.value.text).isEqualTo("Password_123")
    }

    @Test
    fun inputConfirmPassword() {
        composeTestRule.onNodeWithTag(CONFIRM_PASSWORD_TEXT_FIELD)
            .performTextInput("Password_123")
        assertThat(viewModel.confirmPassword.value.text).isEqualTo("Password_123")
    }

    @Test
    fun inputInvalidPassword_error() {
        composeTestRule.onNodeWithTag(PASSWORD_TEXT_FIELD).performTextInput("Password123")
        assertThat(viewModel.password.value.error).isNotEqualTo("")
    }

    @Test
    fun inputInvalidConfirmPassword_error() {
        composeTestRule.onNodeWithTag(CONFIRM_PASSWORD_TEXT_FIELD)
            .performTextInput("Password123")
        assertThat(viewModel.confirmPassword.value.error).isNotEqualTo("")
    }

    @Test
    fun inputPasswordsDoNotMatch_error() {
        composeTestRule.onNodeWithTag(PASSWORD_TEXT_FIELD)
            .performTextInput("Password_123")
        composeTestRule.onNodeWithTag(CONFIRM_PASSWORD_TEXT_FIELD)
            .performTextInput("Password123")
        assertThat(viewModel.confirmPassword.value.error).isNotEqualTo("")
    }

    @Test
    fun inputNickname() {
        composeTestRule.onNodeWithTag(NICKNAME_TEXT_FIELD)
            .performTextInput("Nickname")
        assertThat(viewModel.nickname.value.text).isEqualTo("Nickname")
    }

    @Test
    fun selectedGenre() {
        composeTestRule.onNodeWithText(genres[0]).performClick()
        assertThat(viewModel.selectedInterests).contains(genres[0])
    }

    @Test
    fun deselectedGenre() {
        composeTestRule.onNodeWithText(genres[0]).performClick()
        composeTestRule.onNodeWithText(genres[0]).performClick()
        assertThat(viewModel.selectedInterests).doesNotContain(genres[0])
    }

    @Test
    fun validData_register() {
        composeTestRule.onNodeWithTag(EMAIL_TEXT_FIELD).performTextInput("email@mail.com")
        composeTestRule.onNodeWithTag(PASSWORD_TEXT_FIELD).performTextInput("Password_123")
        composeTestRule.onNodeWithTag(CONFIRM_PASSWORD_TEXT_FIELD).performTextInput("Password_123")
        composeTestRule.onNodeWithTag(NICKNAME_TEXT_FIELD).performTextInput("Nickname")
        viewModel.onEvent(RegisterEvent.ChangedInterest(genres[0]))
        viewModel.onEvent(RegisterEvent.ChangedInterest(genres[1]))
        viewModel.onEvent(RegisterEvent.ChangedInterest(genres[2]))
        composeTestRule.onNodeWithTag(REGISTER_BUTTON).performClick()
    }
}