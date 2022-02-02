package eu.bbsapps.forgottenfilmsapp.domain.use_case.movie.rating

import eu.bbsapps.forgottenfilmsapp.ForgottenFilmsApp.Companion.resource
import eu.bbsapps.forgottenfilmsapp.R
import eu.bbsapps.forgottenfilmsapp.common.Resource
import eu.bbsapps.forgottenfilmsapp.domain.repository.FilmRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class FilmDislikesCountUseCase @Inject constructor(
    private val repository: FilmRepository
) {
    operator fun invoke(id: String): Flow<Resource<Int>> = flow {
        try {
            emit(Resource.Loading<Int>())
            val response = repository.getDislikedCount(id)
            emit(Resource.Success<Int>(response))
        } catch (e: HttpException) {
            emit(
                Resource.Error<Int>(
                    e.localizedMessage ?: resource.getString(R.string.unknown_error_occurred)
                )
            )
        } catch (e: IOException) {
            emit(Resource.Error<Int>(resource.getString(R.string.could_not_reach_server)))
        }
    }
}