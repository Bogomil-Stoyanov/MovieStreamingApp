package eu.bbsapps.forgottenfilmsapp.domain.use_case.film

import eu.bbsapps.forgottenfilmsapp.ForgottenFilmsApp.Companion.resource
import eu.bbsapps.forgottenfilmsapp.R
import eu.bbsapps.forgottenfilmsapp.common.Resource
import eu.bbsapps.forgottenfilmsapp.data.remote.dto.responses.FilmFeedResponse
import eu.bbsapps.forgottenfilmsapp.domain.repository.FilmRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetAllFilmsUseCase @Inject constructor(
    private val repository: FilmRepository
) {
    operator fun invoke(): Flow<Resource<List<FilmFeedResponse>>> = flow {
        try {
            emit(Resource.Loading<List<FilmFeedResponse>>())
            val response = repository.getAllFilms()
            emit(Resource.Success<List<FilmFeedResponse>>(response))
        } catch (e: HttpException) {
            emit(
                Resource.Error<List<FilmFeedResponse>>(
                    e.localizedMessage ?: resource.getString(R.string.unknown_error_occurred)
                )
            )
        } catch (e: IOException) {
            emit(Resource.Error<List<FilmFeedResponse>>(resource.getString(R.string.could_not_reach_server)))
        }
    }
}