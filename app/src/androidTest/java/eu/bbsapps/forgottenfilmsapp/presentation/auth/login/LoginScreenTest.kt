package eu.bbsapps.forgottenfilmsapp.presentation.auth.login

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.NavController
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import com.google.common.truth.Truth.assertThat
import eu.bbsapps.forgottenfilmsapp.common.Constants.ENCRYPTED_SHARED_PREF_NAME
import eu.bbsapps.forgottenfilmsapp.common.TestTags.EMAIL_TEXT_FIELD
import eu.bbsapps.forgottenfilmsapp.common.TestTags.LOGIN_BUTTON
import eu.bbsapps.forgottenfilmsapp.common.TestTags.PASSWORD_TEXT_FIELD
import eu.bbsapps.forgottenfilmsapp.common.TestTags.REMEMBER_ME_CHECKBOX
import eu.bbsapps.forgottenfilmsapp.data.remote.BasicAuthInterceptor
import eu.bbsapps.forgottenfilmsapp.data.repoistory.FakeFilmRepository
import eu.bbsapps.forgottenfilmsapp.domain.use_case.auth.LoginUseCase
import eu.bbsapps.forgottenfilmsapp.presentation.MainActivity
import eu.bbsapps.forgottenfilmsapp.presentation.ui.theme.ForgottenFilmsAppTheme
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LoginScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule(MainActivity::class.java)

    @RelaxedMockK
    lateinit var navController: NavController

    private lateinit var viewModel: LoginViewModel
    private lateinit var sharedPrefs: SharedPreferences
    private lateinit var context: Context

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        context = getApplicationContext()
        sharedPrefs = context.getSharedPreferences(ENCRYPTED_SHARED_PREF_NAME, MODE_PRIVATE)
        viewModel = LoginViewModel(
            LoginUseCase(FakeFilmRepository()),
            sharedPrefs, BasicAuthInterceptor()
        )

        composeTestRule.setContent {
            ForgottenFilmsAppTheme {
                LoginScreen(
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
    fun inputPassword() {
        composeTestRule.onNodeWithTag(PASSWORD_TEXT_FIELD).performTextInput("Password_123")
        assertThat(viewModel.password.value.text).isEqualTo("Password_123")
    }

    @Test
    fun loginSuccessful() {
        composeTestRule.onNodeWithTag(EMAIL_TEXT_FIELD).performTextInput("emial@mail.com")
        assertThat(viewModel.email.value.text).isEqualTo("emial@mail.com")
        composeTestRule.onNodeWithTag(PASSWORD_TEXT_FIELD).performTextInput("Password_123")
        assertThat(viewModel.password.value.text).isEqualTo("Password_123")

        composeTestRule.onNodeWithTag(LOGIN_BUTTON).performClick()
        assertThat(viewModel.state.value.loginSuccessful).isTrue()
    }


    @Test
    fun clickOnceOnRememberMe_rememberMeValueIsTrue() {
        composeTestRule.onNodeWithTag(REMEMBER_ME_CHECKBOX).performClick()
        assertThat(viewModel.rememberMe.value).isTrue()
    }

    @Test
    fun clickTwiceOnRememberMe_rememberMeValueIsFalse() {
        composeTestRule.onNodeWithTag(REMEMBER_ME_CHECKBOX).performClick()
        composeTestRule.onNodeWithTag(REMEMBER_ME_CHECKBOX).performClick()
        assertThat(viewModel.rememberMe.value).isFalse()
    }
}