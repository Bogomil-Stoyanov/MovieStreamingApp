package eu.bbsapps.forgottenfilmsapp.presentation.splash

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.navigation.NavController
import eu.bbsapps.forgottenfilmsapp.common.Constants
import eu.bbsapps.forgottenfilmsapp.presentation.MainActivity
import eu.bbsapps.forgottenfilmsapp.presentation.Screen
import eu.bbsapps.forgottenfilmsapp.presentation.ui.theme.ForgottenFilmsAppTheme
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SplashScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @RelaxedMockK
    lateinit var navController: NavController

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @ExperimentalFoundationApi
    @Test
    fun splashScreen_displaysAndDisappears() = testDispatcher.runBlockingTest {
        composeTestRule.setContent {
            ForgottenFilmsAppTheme {
                SplashScreen(
                    navController = navController,
                    dispatcher = testDispatcher
                )
            }
        }

        composeTestRule
            .onNodeWithContentDescription("Logo")
            .assertExists()

        advanceTimeBy(Constants.SPLASH_SCREEN_DURATION)

        verify {
            navController.popBackStack()
            navController.navigate(Screen.LoginScreen.route)
        }
    }
}