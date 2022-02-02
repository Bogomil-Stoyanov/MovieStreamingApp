package eu.bbsapps.forgottenfilmsapp

import eu.bbsapps.forgottenfilmsapp.presentation.auth.login.LoginViewModelTest
import eu.bbsapps.forgottenfilmsapp.presentation.auth.register.RegisterViewModelTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.runner.RunWith
import org.junit.runners.Suite

@ExperimentalCoroutinesApi
@RunWith(Suite::class)
@Suite.SuiteClasses(
    LoginViewModelTest::class,
    RegisterViewModelTest::class,
)
class ViewModelTestSuite