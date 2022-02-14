package eu.bbsapps.forgottenfilmsapp.data.remote.dto.requests.user.management

data class GenreWatchTimeRequest(
    val genre: String,
    val additionalWatchTimeInSeconds: Int
)