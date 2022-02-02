package eu.bbsapps.forgottenfilmsapp.domain.use_case.movie.movielist

import eu.bbsapps.forgottenfilmsapp.ForgottenFilmsApp.Companion.resource
import eu.bbsapps.forgottenfilmsapp.R
import eu.bbsapps.forgottenfilmsapp.common.Resource
import eu.bbsapps.forgottenfilmsapp.data.remote.dto.responses.FilmFeedItem
import eu.bbsapps.forgottenfilmsapp.domain.repository.FilmRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetFilmListUseCase @Inject constructor(
    private val repository: FilmRepository
) {
    operator fun invoke(): Flow<Resource<List<FilmFeedItem>>> = flow {
        try {
            emit(Resource.Loading<List<FilmFeedItem>>())
            val response = repository.getAllFilmsFromList()
            emit(Resource.Success<List<FilmFeedItem>>(response))
        } catch (e: HttpException) {
            emit(
                Resource.Error<List<FilmFeedItem>>(
                    e.localizedMessage ?: resource.getString(R.string.unknown_error_occurred)
                )
            )
        } catch (e: IOException) {
            emit(Resource.Error<List<FilmFeedItem>>(resource.getString(R.string.could_not_reach_server)))
        }
    }
}