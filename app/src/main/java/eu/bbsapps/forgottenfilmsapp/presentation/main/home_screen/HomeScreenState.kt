package eu.bbsapps.forgottenfilmsapp.presentation.main.home_screen

import eu.bbsapps.forgottenfilmsapp.data.remote.dto.responses.FilmFeedResponse

data class HomeScreenState(
    val movie: List<FilmFeedResponse>? = null,
    val isLoading: Boolean = false,
    val error: String = ""
)
