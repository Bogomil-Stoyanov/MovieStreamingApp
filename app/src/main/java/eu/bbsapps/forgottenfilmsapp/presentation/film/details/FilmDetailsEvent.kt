package eu.bbsapps.forgottenfilmsapp.presentation.film.details

sealed class FilmDetailsEvent {
    object LikeClicked : FilmDetailsEvent()
    object DislikeClicked : FilmDetailsEvent()
    object AddToList : FilmDetailsEvent()
    data class ClickedImage(val imageUrl: String) : FilmDetailsEvent()
    object GalleryClosed : FilmDetailsEvent()
    object NextImageClicked : FilmDetailsEvent()
    object PreviousImageClicked : FilmDetailsEvent()
}

