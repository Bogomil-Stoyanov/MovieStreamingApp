package eu.bbsapps.forgottenfilmsapp.domain.use_case.movie.rating

data class FilmRatingUseCases(
    val filmLikedUseCase: FilmLikedUseCase,
    val filmDislikedUseCase: FilmDislikedUseCase,
    val filmLikesCountUseCase: FilmLikesCountUseCase,
    val filmDislikesCountUseCase: FilmDislikesCountUseCase
)