package eu.bbsapps.forgottenfilmsapp.data.remote.dto.responses

/**
 * @param name The title of the film
 * @param image The main image of the film
 */
data class FilmFeedItem(
    val name: String,
    val image: String,
    val id: String
)
