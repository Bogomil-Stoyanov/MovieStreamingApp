package eu.bbsapps.forgottenfilmsapp.presentation.components

data class OutlinedTextFieldState(
    val text: String = "",
    val hint: String = "",
    val error: String = "",
    val isHintVisible: Boolean = true
)
