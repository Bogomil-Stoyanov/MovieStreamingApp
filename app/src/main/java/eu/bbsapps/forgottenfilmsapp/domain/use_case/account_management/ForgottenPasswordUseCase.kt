package eu.bbsapps.forgottenfilmsapp.domain.use_case.account_management

import eu.bbsapps.forgottenfilmsapp.ForgottenFilmsApp
import eu.bbsapps.forgottenfilmsapp.R
import eu.bbsapps.forgottenfilmsapp.common.Resource
import eu.bbsapps.forgottenfilmsapp.data.remote.dto.responses.SimpleResponse
import eu.bbsapps.forgottenfilmsapp.domain.repository.FilmRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ForgottenPasswordUseCase @Inject constructor(
    private val repository: FilmRepository
) {
    operator fun invoke(email: String): Flow<Resource<SimpleResponse>> = flow {
        try {
            emit(Resource.Loading<SimpleResponse>())
            val response = repository.forgottenPassword(email)
            emit(Resource.Success<SimpleResponse>(response))
        } catch (e: HttpException) {
            println("HTT")
            e.printStackTrace()
            emit(
                Resource.Error<SimpleResponse>(
                    e.localizedMessage
                        ?: ForgottenFilmsApp.resource.getString(R.string.unknown_error_occurred)
                )
            )
        } catch (e: IOException) {
            println("IO")
            e.printStackTrace()
            emit(Resource.Error<SimpleResponse>(ForgottenFilmsApp.resource.getString(R.string.could_not_reach_server)))
        }
    }
}