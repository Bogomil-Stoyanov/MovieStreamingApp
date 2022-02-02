package eu.bbsapps.forgottenfilmsapp.presentation.auth.login

data class LoginState(
    val isLoading: Boolean = false,
    val error: String = "",
    val loginSuccessful: Boolean? = null,
)