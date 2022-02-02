package eu.bbsapps.forgottenfilmsapp.presentation.main.search_screen

sealed class SearchEvent {
    data class EnteredQuery(val query: String) : SearchEvent()
    object SearchClicked : SearchEvent()
}
