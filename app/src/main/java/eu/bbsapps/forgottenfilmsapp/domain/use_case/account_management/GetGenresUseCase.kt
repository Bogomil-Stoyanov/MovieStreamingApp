package eu.bbsapps.forgottenfilmsapp.domain.use_case.account_management

import eu.bbsapps.forgottenfilmsapp.ForgottenFilmsApp.Companion.resource
import eu.bbsapps.forgottenfilmsapp.R
import eu.bbsapps.forgottenfilmsapp.common.Resource
import eu.bbsapps.forgottenfilmsapp.domain.repository.FilmRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetGenresUseCase @Inject constructor(
    private val repository: FilmRepository
) {
    operator fun invoke(): Flow<Resource<List<String>>> = flow {
        try {
            emit(Resource.Loading<List<String>>())
            val response = repository.getInterests()
            emit(Resource.Success<List<String>>(response))
        } catch (e: HttpException) {
            emit(
                Resource.Error<List<String>>(
                    e.localizedMessage ?: resource.getString(R.string.unknown_error_occurred)
                )
            )
        } catch (e: IOException) {
            e.printStackTrace()
            emit(Resource.Error<List<String>>(resource.getString(R.string.could_not_reach_server)))
        }
    }
}