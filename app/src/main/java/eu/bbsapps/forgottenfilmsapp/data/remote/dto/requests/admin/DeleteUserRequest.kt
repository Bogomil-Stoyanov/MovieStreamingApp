package eu.bbsapps.forgottenfilmsapp.data.remote.dto.requests.admin

data class DeleteUserRequest(
    val userEmail: String,
    val apiKey: String
)
