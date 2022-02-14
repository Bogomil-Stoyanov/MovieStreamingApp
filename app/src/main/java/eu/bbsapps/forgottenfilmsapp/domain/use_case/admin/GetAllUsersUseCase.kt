package eu.bbsapps.forgottenfilmsapp.domain.use_case.admin

import eu.bbsapps.forgottenfilmsapp.ForgottenFilmsApp.Companion.resource
import eu.bbsapps.forgottenfilmsapp.R
import eu.bbsapps.forgottenfilmsapp.common.Resource
import eu.bbsapps.forgottenfilmsapp.data.remote.dto.responses.UserResponse
import eu.bbsapps.forgottenfilmsapp.domain.repository.FilmRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetAllUsersUseCase @Inject constructor(
    private val repository: FilmRepository
) {
    operator fun invoke(): Flow<Resource<List<UserResponse>>> = flow {
        try {
            emit(Resource.Loading<List<UserResponse>>())
            val response = repository.getAllUsers()
            emit(Resource.Success<List<UserResponse>>(response))
        } catch (e: HttpException) {
            emit(
                Resource.Error<List<UserResponse>>(
                    e.localizedMessage
                        ?: resource.getString(R.string.unknown_error_occurred)
                )
            )
        } catch (e: IOException) {
            e.printStackTrace()
            emit(Resource.Error<List<UserResponse>>(resource.getString(R.string.could_not_reach_server)))
        }
    }
}