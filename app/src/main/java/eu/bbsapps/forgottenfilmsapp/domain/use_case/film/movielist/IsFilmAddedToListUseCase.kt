package eu.bbsapps.forgottenfilmsapp.domain.use_case.film.movielist

import eu.bbsapps.forgottenfilmsapp.ForgottenFilmsApp.Companion.resource
import eu.bbsapps.forgottenfilmsapp.R
import eu.bbsapps.forgottenfilmsapp.common.Resource
import eu.bbsapps.forgottenfilmsapp.domain.repository.FilmRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class IsFilmAddedToListUseCase @Inject constructor(
    private val repository: FilmRepository
) {
    operator fun invoke(id: String): Flow<Resource<Boolean>> = flow {
        try {
            emit(Resource.Loading<Boolean>())
            val response = repository.isFilmAddedToList(id)
            emit(Resource.Success<Boolean>(response))
        } catch (e: HttpException) {
            emit(
                Resource.Error<Boolean>(
                    e.localizedMessage ?: resource.getString(R.string.unknown_error_occurred)
                )
            )
        } catch (e: IOException) {
            emit(Resource.Error<Boolean>(resource.getString(R.string.could_not_reach_server)))
        }
    }
}