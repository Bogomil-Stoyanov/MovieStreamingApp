package eu.bbsapps.forgottenfilmsapp.data.remote.dto.responses

data class FilmFeedResponse(
    val title: String,
    val films: List<FilmFeedItem>
)
