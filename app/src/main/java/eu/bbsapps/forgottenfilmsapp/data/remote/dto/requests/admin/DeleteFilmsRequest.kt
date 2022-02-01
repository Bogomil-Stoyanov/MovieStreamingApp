package eu.bbsapps.forgottenfilmsapp.data.remote.dto.requests.admin

data class DeleteFilmRequest(
    val names: List<String>,
    val apiKey: String
)
