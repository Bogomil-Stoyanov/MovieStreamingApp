package eu.bbsapps.forgottenfilmsapp.domain.use_case.account_management

import eu.bbsapps.forgottenfilmsapp.ForgottenFilmsApp.Companion.resource
import eu.bbsapps.forgottenfilmsapp.R
import eu.bbsapps.forgottenfilmsapp.common.Resource
import eu.bbsapps.forgottenfilmsapp.data.remote.dto.responses.GenreWatchTimePair
import eu.bbsapps.forgottenfilmsapp.domain.repository.FilmRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetWatchTimeUseCase @Inject constructor(
    private val repository: FilmRepository
) {
    operator fun invoke(): Flow<Resource<List<GenreWatchTimePair>>> = flow {
        try {
            emit(Resource.Loading<List<GenreWatchTimePair>>())
            val response = repository.getTotalWatchTime()
            emit(Resource.Success<List<GenreWatchTimePair>>(response))
        } catch (e: HttpException) {
            emit(
                Resource.Error<List<GenreWatchTimePair>>(
                    e.localizedMessage ?: resource.getString(R.string.unknown_error_occurred)
                )
            )
        } catch (e: IOException) {
            emit(Resource.Error<List<GenreWatchTimePair>>(resource.getString(R.string.could_not_reach_server)))
        }
    }
}