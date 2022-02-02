package eu.bbsapps.forgottenfilmsapp.domain.use_case.auth

import eu.bbsapps.forgottenfilmsapp.ForgottenFilmsApp.Companion.resource
import eu.bbsapps.forgottenfilmsapp.R
import eu.bbsapps.forgottenfilmsapp.common.Resource
import eu.bbsapps.forgottenfilmsapp.data.remote.dto.requests.user.register.CreateAccountRequest
import eu.bbsapps.forgottenfilmsapp.data.remote.dto.responses.SimpleResponse
import eu.bbsapps.forgottenfilmsapp.domain.repository.FilmRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: FilmRepository
) {
    operator fun invoke(createAccountRequest: CreateAccountRequest): Flow<Resource<SimpleResponse>> =
        flow {
            try {
                emit(Resource.Loading<SimpleResponse>())
                val response = repository.register(createAccountRequest)
                emit(Resource.Success<SimpleResponse>(response))
            } catch (e: HttpException) {
                emit(
                    Resource.Error<SimpleResponse>(
                        e.localizedMessage ?: resource.getString(R.string.unknown_error_occurred)
                    )
                )
            } catch (e: IOException) {
                emit(Resource.Error<SimpleResponse>(resource.getString(R.string.could_not_reach_server)))
            }
        }
}