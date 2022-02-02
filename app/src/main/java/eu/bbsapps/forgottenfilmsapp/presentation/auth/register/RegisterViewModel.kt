package eu.bbsapps.forgottenfilmsapp.presentation.auth.register

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.bbsapps.forgottenfilmsapp.ForgottenFilmsApp.Companion.resource
import eu.bbsapps.forgottenfilmsapp.R
import eu.bbsapps.forgottenfilmsapp.common.Resource
import eu.bbsapps.forgottenfilmsapp.data.remote.dto.requests.user.register.CreateAccountRequest
import eu.bbsapps.forgottenfilmsapp.domain.use_case.auth.RegisterUseCase
import eu.bbsapps.forgottenfilmsapp.domain.use_case.film.GetAllGenresUseCase
import eu.bbsapps.forgottenfilmsapp.presentation.auth.components.PasswordTextFieldState
import eu.bbsapps.forgottenfilmsapp.presentation.components.OutlinedTextFieldState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val getAllGenresUseCase: GetAllGenresUseCase
) : ViewModel() {

    private val _state = mutableStateOf(RegisterState())
    val state: State<RegisterState> = _state

    private val _email =
        mutableStateOf(OutlinedTextFieldState(hint = resource.getString(R.string.email)))
    val email: State<OutlinedTextFieldState> = _email

    private val _password =
        mutableStateOf(PasswordTextFieldState(hint = resource.getString(R.string.password)))
    val password: State<PasswordTextFieldState> = _password

    private val _confirmPassword =
        mutableStateOf(PasswordTextFieldState(hint = resource.getString(R.string.confirm_password)))
    val confirmPassword: State<PasswordTextFieldState> = _confirmPassword

    private val _nickname =
        mutableStateOf(OutlinedTextFieldState(hint = resource.getString(R.string.nickname)))
    val nickname: State<OutlinedTextFieldState> = _nickname

    private val _selectedInterests = mutableStateListOf<String>()
    val selectedInterests: SnapshotStateList<String> = _selectedInterests

    private val _isRegisterButtonEnabled = mutableStateOf(false)
    val isRegisterButtonEnabled: State<Boolean> = _isRegisterButtonEnabled

    private val _isPrivacyPolicyDialogVisible = mutableStateOf(false)
    val isPrivacyPolicyDialogVisible: State<Boolean> = _isPrivacyPolicyDialogVisible

    private val _agreesToPrivacyPolicy = mutableStateOf(false)
    val agreesToPrivacyPolicy: State<Boolean> = _agreesToPrivacyPolicy

    private val _genres = mutableStateListOf<String>()
    val generes: SnapshotStateList<String> = _genres

    init {
        getAllGenres()
    }

    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.EnteredEmail -> {
                _email.value = email.value.copy(text = event.value)

                val emailPattern = ("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                        + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$").toRegex()

                if (!emailPattern.matches(email.value.text)) {
                    _email.value =
                        email.value.copy(error = resource.getString(R.string.enter_valid_email))
                } else {
                    if (email.value.error.isNotBlank()) {
                        _email.value = email.value.copy(error = "")
                    }
                }
                checkRegisterButtonEnabled()
            }
            is RegisterEvent.ChangeEmailFocus -> {
                _email.value = email.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            email.value.text.isBlank()
                )
            }
            is RegisterEvent.EnteredPassword -> {
                _password.value = password.value.copy(text = event.value)
                val passwordPattern =
                    "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=_])(?=\\S+$).{8,}".toRegex()
                if (!passwordPattern.matches(password.value.text)) {
                    _password.value =
                        password.value.copy(error = resource.getString(R.string.enter_valid_password))
                } else {
                    if (password.value.error.isNotBlank()) {
                        _password.value = password.value.copy(error = "")
                    }
                }
                checkRegisterButtonEnabled()
            }
            is RegisterEvent.ChangePasswordFocus -> {
                _password.value = password.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            password.value.text.isBlank()
                )
            }
            is RegisterEvent.EnteredConfirmPassword -> {
                _confirmPassword.value = confirmPassword.value.copy(text = event.value)
                if (confirmPassword.value.text != password.value.text) {
                    _confirmPassword.value =
                        confirmPassword.value.copy(error = resource.getString(R.string.passwords_do_not_match))
                } else {
                    if (_confirmPassword.value.text.isNotBlank()) {
                        _confirmPassword.value = confirmPassword.value.copy(error = "")
                    }
                }
                checkRegisterButtonEnabled()
            }

            is RegisterEvent.ChangeConfirmPasswordFocus -> {
                _confirmPassword.value = confirmPassword.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            confirmPassword.value.text.isBlank()
                )
            }

            is RegisterEvent.EnteredNickname -> {
                _nickname.value = nickname.value.copy(text = event.value)

                if (nickname.value.text.isBlank()) {
                    _nickname.value =
                        nickname.value.copy(error = resource.getString(R.string.nickname_not_blank))
                } else {
                    if (nickname.value.error.isNotBlank()) {
                        _nickname.value = nickname.value.copy(error = "")
                    }
                }
                checkRegisterButtonEnabled()
            }

            is RegisterEvent.ChangeNicknameFocus -> {
                _nickname.value = nickname.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            nickname.value.text.isBlank()
                )
            }
            RegisterEvent.RegisterClicked -> {
                if (isRegisterButtonEnabled.value)
                    register()
            }
            is RegisterEvent.ChangedInterest -> {
                if (_selectedInterests.contains(event.interest)) {
                    _selectedInterests.remove(event.interest)
                } else {
                    _selectedInterests.add(event.interest)
                }
                checkRegisterButtonEnabled()
            }
            is RegisterEvent.ChangePasswordVisibility -> {
                _password.value = password.value.copy(isPasswordVisible = event.isVisible)
            }
            is RegisterEvent.ChangeConfirmPasswordVisibility -> {
                _confirmPassword.value =
                    confirmPassword.value.copy(isPasswordVisible = event.isVisible)
            }
            RegisterEvent.PrivacyPolicyClicked -> {
                _isPrivacyPolicyDialogVisible.value = true
            }
            RegisterEvent.PrivacyPolicyDialogCanceled -> {
                _isPrivacyPolicyDialogVisible.value = false
            }
            RegisterEvent.PrivacyPolicyAgreeClicked -> {
                println("HERE")
                _agreesToPrivacyPolicy.value = !agreesToPrivacyPolicy.value
                checkRegisterButtonEnabled()
            }
        }
    }

    private fun getAllGenres() {
        getAllGenresUseCase().onEach { result ->
            if (result is Resource.Error) {
                _state.value =
                    RegisterState(
                        error = result.message
                            ?: resource.getString(R.string.unknown_error_occurred)
                    )
            } else if (result is Resource.Success) {
                _genres.addAll(
                    if (result.data?.isNotEmpty() == true) {
                        result.data
                    } else emptyList()
                )
            }
        }.launchIn(viewModelScope)
    }


    private fun register() {
        registerUseCase(
            CreateAccountRequest(
                email.value.text,
                password.value.text,
                nickname.value.text,
                selectedInterests
            )
        ).onEach { result ->
            when (result) {
                is Resource.Error -> {
                    _state.value =
                        RegisterState(
                            error = result.message
                                ?: resource.getString(R.string.unknown_error_occurred)
                        )
                }
                is Resource.Loading -> {
                    _state.value = RegisterState(isLoading = true)
                }
                is Resource.Success -> {
                    _state.value = if (result.data?.successful == true) {
                        RegisterState(registerSuccessful = result.data.successful)
                    } else RegisterState(
                        error = result.data?.message
                            ?: resource.getString(R.string.unknown_error_occurred)
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun checkRegisterButtonEnabled() {
        _isRegisterButtonEnabled.value =
            email.value.error.isBlank()
                    && password.value.error.isBlank()
                    && confirmPassword.value.error.isBlank()
                    && selectedInterests.size >= 3
                    && nickname.value.error.isBlank()
                    && email.value.text.isNotBlank()
                    && password.value.text.isNotBlank()
                    && confirmPassword.value.text.isNotBlank()
                    && nickname.value.text.isNotBlank()
                    && agreesToPrivacyPolicy.value
    }
}