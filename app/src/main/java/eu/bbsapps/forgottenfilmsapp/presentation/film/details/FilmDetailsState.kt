package eu.bbsapps.forgottenfilmsapp.presentation.film.details

import eu.bbsapps.forgottenfilmsapp.data.remote.dto.responses.FilmResponse

data class FilmDetailsState(
    val film: FilmResponse? = null,
    val isLoading: Boolean = false,
    val error: String = ""
)
