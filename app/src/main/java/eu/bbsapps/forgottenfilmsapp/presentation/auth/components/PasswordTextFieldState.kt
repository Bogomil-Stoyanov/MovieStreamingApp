package eu.bbsapps.forgottenfilmsapp.presentation.auth.components

data class PasswordTextFieldState(
    val text: String = "",
    val hint: String = "",
    val error: String = "",
    val isHintVisible: Boolean = true,
    val isPasswordVisible: Boolean = false
)
