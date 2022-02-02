package eu.bbsapps.forgottenfilmsapp

import eu.bbsapps.filmstreamingapp.presentation.film.details.FilmDetailsViewModelTest
import eu.bbsapps.forgottenfilmsapp.presentation.auth.login.LoginViewModelTest
import eu.bbsapps.forgottenfilmsapp.presentation.auth.register.RegisterViewModelTest
import eu.bbsapps.forgottenfilmsapp.presentation.main.home_screen.HomeViewModelTest
import eu.bbsapps.forgottenfilmsapp.presentation.main.more_screen.MoreViewModelTest
import eu.bbsapps.forgottenfilmsapp.presentation.main.my_list_screen.MyListViewModelTest
import eu.bbsapps.forgottenfilmsapp.presentation.main.search_screen.SearchViewModelTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.runner.RunWith
import org.junit.runners.Suite

@ExperimentalCoroutinesApi
@RunWith(Suite::class)
@Suite.SuiteClasses(
    LoginViewModelTest::class,
    RegisterViewModelTest::class,
    HomeViewModelTest::class,
    MoreViewModelTest::class,
    MyListViewModelTest::class,
    SearchViewModelTest::class,
    FilmDetailsViewModelTest::class
)
class ViewModelTestSuite