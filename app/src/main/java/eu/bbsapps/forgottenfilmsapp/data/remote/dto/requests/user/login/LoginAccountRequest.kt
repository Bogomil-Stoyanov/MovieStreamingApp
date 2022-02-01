package eu.bbsapps.forgottenfilmsapp.data.remote.dto.requests.user.login

data class LoginAccountRequest(
    val email: String,
    val password: String
)