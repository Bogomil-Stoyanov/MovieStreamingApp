package eu.bbsapps.forgottenfilmsapp

import eu.bbsapps.forgottenfilmsapp.presentation.auth.login.LoginScreenTest
import eu.bbsapps.forgottenfilmsapp.presentation.auth.register.RegisterScreenTest
import eu.bbsapps.forgottenfilmsapp.presentation.splash.SplashScreenTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.runner.RunWith
import org.junit.runners.Suite

@ExperimentalCoroutinesApi
@RunWith(Suite::class)
@Suite.SuiteClasses(
    LoginScreenTest::class,
    RegisterScreenTest::class,
    SplashScreenTest::class
)
class UiTestSuite
