package eu.bbsapps.forgottenfilmsapp.data.remote.dto.requests.admin

import eu.bbsapps.forgottenfilmsapp.data.remote.dto.responses.FilmResponse

data class AddFilmsRequest(
    val films: List<FilmResponse>
)