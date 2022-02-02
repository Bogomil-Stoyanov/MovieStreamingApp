package eu.bbsapps.forgottenfilmsapp.domain.use_case.admin

import eu.bbsapps.forgottenfilmsapp.ForgottenFilmsApp.Companion.resource
import eu.bbsapps.forgottenfilmsapp.R
import eu.bbsapps.forgottenfilmsapp.common.Resource
import eu.bbsapps.forgottenfilmsapp.data.remote.dto.requests.admin.AddFilmsRequest
import eu.bbsapps.forgottenfilmsapp.data.remote.dto.requests.admin.Film
import eu.bbsapps.forgottenfilmsapp.domain.repository.FilmRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class AddFilmsUseCase @Inject constructor(
    private val repository: FilmRepository
) {
    operator fun invoke(film: Film): Flow<Resource<Response<Unit>>> = flow {
        try {
            emit(Resource.Loading<Response<Unit>>())
            val response = repository.addFilms(AddFilmsRequest(listOf(film)))
            emit(Resource.Success<Response<Unit>>(response))
        } catch (e: HttpException) {
            emit(
                Resource.Error<Response<Unit>>(
                    e.localizedMessage ?: resource.getString(R.string.unknown_error_occurred)
                )
            )
        } catch (e: IOException) {
            e.printStackTrace()
            emit(Resource.Error<Response<Unit>>(resource.getString(R.string.could_not_reach_server)))
        }
    }
}