package eu.bbsapps.forgottenfilmsapp.domain.use_case.film.list

data class FilmListUseCases(
    val addFilmToListUseCase: AddFilmToListUseCase,
    val removeFilmFromListUseCase: RemoveFilmFromListUseCase,
    val isFilmAddedToListUseCase: IsFilmAddedToListUseCase
)
