package eu.bbsapps.forgottenfilmsapp.presentation.auth.register

data class RegisterState(
    val isLoading: Boolean = false,
    val error: String = "",
    val registerSuccessful: Boolean? = null,
)