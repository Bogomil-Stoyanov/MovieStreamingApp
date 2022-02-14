package eu.bbsapps.forgottenfilmsapp.domain.use_case.admin

import eu.bbsapps.forgottenfilmsapp.ForgottenFilmsApp.Companion.resource
import eu.bbsapps.forgottenfilmsapp.R
import eu.bbsapps.forgottenfilmsapp.common.Resource
import eu.bbsapps.forgottenfilmsapp.domain.repository.FilmRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetUsersCountUseCase @Inject constructor(
    private val repository: FilmRepository
) {
    operator fun invoke(): Flow<Resource<Int>> = flow {
        try {
            emit(Resource.Loading<Int>())
            val response = repository.getUsersCount()
            emit(Resource.Success<Int>(response))
        } catch (e: HttpException) {
            emit(
                Resource.Error<Int>(
                    e.localizedMessage ?: resource.getString(R.string.unknown_error_occurred)
                )
            )
        } catch (e: IOException) {
            e.printStackTrace()
            emit(Resource.Error<Int>(resource.getString(R.string.could_not_reach_server)))
        }
    }
}