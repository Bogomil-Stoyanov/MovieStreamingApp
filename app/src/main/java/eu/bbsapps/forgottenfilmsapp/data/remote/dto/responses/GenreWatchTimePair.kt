package eu.bbsapps.forgottenfilmsapp.data.remote.dto.responses

data class GenreWatchTimePair(
    val genre: String,
    val totalWatchTimeInSeconds: Int
)