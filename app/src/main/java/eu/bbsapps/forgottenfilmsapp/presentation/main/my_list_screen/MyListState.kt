package eu.bbsapps.forgottenfilmsapp.presentation.main.my_list_screen

import eu.bbsapps.forgottenfilmsapp.data.remote.dto.responses.FilmFeedItem

data class MyListState(
    val filmList: List<FilmFeedItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = ""
)