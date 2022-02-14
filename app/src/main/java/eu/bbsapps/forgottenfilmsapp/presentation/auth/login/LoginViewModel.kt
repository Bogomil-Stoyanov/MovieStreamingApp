package eu.bbsapps.forgottenfilmsapp.presentation.auth.login

import android.content.SharedPreferences
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.bbsapps.forgottenfilmsapp.ForgottenFilmsApp.Companion.resource
import eu.bbsapps.forgottenfilmsapp.R
import eu.bbsapps.forgottenfilmsapp.common.Constants.KEY_LOGGED_IN_EMAIL
import eu.bbsapps.forgottenfilmsapp.common.Constants.KEY_PASSWORD
import eu.bbsapps.forgottenfilmsapp.common.Constants.NO_EMAIL
import eu.bbsapps.forgottenfilmsapp.common.Constants.NO_PASSWORD
import eu.bbsapps.forgottenfilmsapp.common.Resource
import eu.bbsapps.forgottenfilmsapp.data.remote.BasicAuthInterceptor
import eu.bbsapps.forgottenfilmsapp.data.remote.dto.requests.user.login.LoginAccountRequest
import eu.bbsapps.forgottenfilmsapp.domain.use_case.account_management.ForgottenPasswordUseCase
import eu.bbsapps.forgottenfilmsapp.domain.use_case.auth.LoginUseCase
import eu.bbsapps.forgottenfilmsapp.presentation.auth.components.PasswordTextFieldState
import eu.bbsapps.forgottenfilmsapp.presentation.components.OutlinedTextFieldState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val forgottenPasswordUseCase: ForgottenPasswordUseCase,
    private val sharedPref: SharedPreferences,
    private val basicAuthInterceptor: BasicAuthInterceptor
) : ViewModel() {

    private var curEmail: String? = null
    private var curPassword: String? = null

    private val _state = mutableStateOf(LoginState())
    val state: State<LoginState> = _state

    private val _email =
        mutableStateOf(OutlinedTextFieldState(hint = resource.getString(R.string.email)))
    val email: State<OutlinedTextFieldState> = _email

    private val _password =
        mutableStateOf(PasswordTextFieldState(hint = resource.getString(R.string.password)))
    val password: State<PasswordTextFieldState> = _password

    private val _forgottenEmail =
        mutableStateOf(OutlinedTextFieldState(hint = resource.getString(R.string.forgottenPasswordEmail)))
    val forgottenEmail: State<OutlinedTextFieldState> = _forgottenEmail

    private val _rememberMe = mutableStateOf(false)
    val rememberMe: State<Boolean> = _rememberMe

    private val _forgottenPasswordDialogVisible = mutableStateOf(false)
    val forgottenPasswordDialogVisible: State<Boolean> = _forgottenPasswordDialogVisible

    init {
        curEmail = sharedPref.getString(KEY_LOGGED_IN_EMAIL, NO_EMAIL) ?: NO_EMAIL
        curPassword = sharedPref.getString(KEY_PASSWORD, NO_PASSWORD) ?: NO_PASSWORD

        if (curEmail != NO_EMAIL && curPassword != NO_PASSWORD) {
            authenticateApi(
                sharedPref.getString(KEY_LOGGED_IN_EMAIL, NO_EMAIL)!!,
                sharedPref.getString(KEY_PASSWORD, NO_PASSWORD)!!
            )
            _state.value = LoginState(loginSuccessful = true)
        }
    }

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EnteredEmail -> {
                _email.value = email.value.copy(text = event.value)
            }
            is LoginEvent.ChangeEmailFocus -> {
                _email.value = email.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            email.value.text.isBlank()
                )
            }
            is LoginEvent.EnteredPassword -> {
                _password.value = password.value.copy(text = event.value)
            }
            is LoginEvent.ChangePasswordFocus -> {
                _password.value = password.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            password.value.text.isBlank()
                )
            }
            LoginEvent.LoginClicked -> {
                logIn()
            }
            LoginEvent.RememberMeClicked -> {
                _rememberMe.value = !rememberMe.value
            }
            is LoginEvent.ChangePasswordVisibility -> {
                _password.value = password.value.copy(isPasswordVisible = event.isVisible)
            }
            is LoginEvent.ChangeForgottenPasswordEmailFocus -> {
                _forgottenEmail.value = forgottenEmail.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            forgottenEmail.value.text.isBlank()
                )
            }
            is LoginEvent.EnteredForgottenPasswordEmail -> {
                _forgottenEmail.value = forgottenEmail.value.copy(text = event.value)
            }
            LoginEvent.ForgottenPasswordClicked -> {
                _forgottenPasswordDialogVisible.value = true
            }
            LoginEvent.ForgottenPasswordDialogDismissed -> {
                _forgottenPasswordDialogVisible.value = false
                _forgottenEmail.value = forgottenEmail.value.copy(text = "")
            }
            LoginEvent.SendResetPasswordClicked -> {
                forgottenPassword(forgottenEmail.value.text)
                _forgottenEmail.value = forgottenEmail.value.copy(text = "")
            }
        }
    }

    private fun forgottenPassword(email: String) {
        forgottenPasswordUseCase(email).onEach { result ->
            when (result) {
                is Resource.Error -> {
                    _state.value =
                        LoginState(
                            error = result.message
                                ?: resource.getString(R.string.unknown_error_occurred)
                        )
                }
                is Resource.Loading -> {
                    _state.value = LoginState(isLoading = true)
                }
                is Resource.Success -> {
                    _state.value = LoginState(
                        error = result.data?.message
                            ?: resource.getString(R.string.recovery_email_sent)
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun logIn() {
        curEmail = _email.value.text
        curPassword = _password.value.text
        loginUseCase(
            LoginAccountRequest(
                email.value.text,
                password.value.text
            )
        ).onEach { result ->
            when (result) {
                is Resource.Error -> {
                    _state.value =
                        LoginState(
                            error = result.message
                                ?: resource.getString(R.string.unknown_error_occurred)
                        )
                }
                is Resource.Loading -> {
                    _state.value = LoginState(isLoading = true)
                }
                is Resource.Success -> {
                    _state.value = if (result.data?.successful == true) {
                        if (rememberMe.value) saveLoginCredentials()
                        authenticateApi(curEmail ?: "", curPassword ?: "")
                        LoginState(loginSuccessful = result.data.successful)
                    } else LoginState(
                        error = result.data?.message
                            ?: resource.getString(R.string.unknown_error_occurred)
                    )
                }
            }
        }.launchIn(viewModelScope)
    }


    private fun saveLoginCredentials() {
        sharedPref.edit().putString(KEY_LOGGED_IN_EMAIL, curEmail).apply()
        sharedPref.edit().putString(KEY_PASSWORD, curPassword).apply()
        authenticateApi(curEmail ?: "", curPassword ?: "")
    }

    private fun authenticateApi(email: String, password: String) {
        basicAuthInterceptor.email = email
        basicAuthInterceptor.password = password
    }
}