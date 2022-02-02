package eu.bbsapps.forgottenfilmsapp.domain.use_case.movie

import eu.bbsapps.forgottenfilmsapp.ForgottenFilmsApp.Companion.resource
import eu.bbsapps.forgottenfilmsapp.R
import eu.bbsapps.forgottenfilmsapp.common.Resource
import eu.bbsapps.forgottenfilmsapp.data.remote.dto.responses.FilmResponse
import eu.bbsapps.forgottenfilmsapp.domain.repository.FilmRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class FilmDetailsUseCase @Inject constructor(
    private val repository: FilmRepository
) {
    operator fun invoke(id: String): Flow<Resource<FilmResponse>> = flow {
        try {
            emit(Resource.Loading<FilmResponse>())
            val response = repository.getFilm(id)
            emit(Resource.Success<FilmResponse>(response))
        } catch (e: HttpException) {
            emit(
                Resource.Error<FilmResponse>(
                    e.localizedMessage ?: resource.getString(R.string.unknown_error_occurred)
                )
            )
        } catch (e: IOException) {
            emit(Resource.Error<FilmResponse>(resource.getString(R.string.could_not_reach_server)))
        }
    }
}