package eu.bbsapps.forgottenfilmsapp.data.remote.dto.requests.user.register

data class CreateAccountRequest(
    val email: String,
    val password: String,
    val nickname: String,
    val genres: List<String>
)