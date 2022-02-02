package eu.bbsapps.forgottenfilmsapp.presentation.main.films_screen

import eu.bbsapps.forgottenfilmsapp.data.remote.dto.responses.FilmFeedResponse

data class FilmState(
    val film: List<FilmFeedResponse>? = null,
    val isLoading: Boolean = false,
    val error: String = ""
)
