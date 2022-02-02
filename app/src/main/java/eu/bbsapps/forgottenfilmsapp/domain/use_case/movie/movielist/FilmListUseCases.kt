package eu.bbsapps.forgottenfilmsapp.domain.use_case.movie.movielist

data class FilmListUseCases(
    val addFilmToListUseCase: AddFilmToListUseCase,
    val removeFilmFromListUseCase: RemoveFilmFromListUseCase,
    val isFilmAddedToListUseCase: IsFilmAddedToListUseCase
)
