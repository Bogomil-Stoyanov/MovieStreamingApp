package eu.bbsapps.forgottenfilmsapp.presentation.main.search_screen

import eu.bbsapps.forgottenfilmsapp.data.remote.dto.responses.FilmFeedItem

data class SearchState(
    val films: List<FilmFeedItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = "",
    val hasSearched: Boolean = false
)
