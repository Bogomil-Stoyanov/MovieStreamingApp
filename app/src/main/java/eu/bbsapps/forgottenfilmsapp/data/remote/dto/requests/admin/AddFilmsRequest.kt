package eu.bbsapps.forgottenfilmsapp.data.remote.dto.requests.admin

data class AddFilmsRequest(
    val films: List<Film>
)

data class Film(
    val name: String,
    val imageUrls: List<String>,
    val description: String,
    val genres: List<String>,
    val likedBy: List<String>,
    val dislikedBy: List<String>,
    val url: String
)