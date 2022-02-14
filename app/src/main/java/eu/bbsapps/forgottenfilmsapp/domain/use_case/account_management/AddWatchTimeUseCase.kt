package eu.bbsapps.forgottenfilmsapp.domain.use_case.account_management

import eu.bbsapps.forgottenfilmsapp.common.Resource
import eu.bbsapps.forgottenfilmsapp.data.remote.dto.requests.user.management.GenreWatchTimeRequest
import eu.bbsapps.forgottenfilmsapp.data.remote.dto.responses.SimpleResponse
import eu.bbsapps.forgottenfilmsapp.domain.repository.FilmRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddWatchTimeUseCase @Inject constructor(
    private val repository: FilmRepository
) {
    operator fun invoke(watchTimeRequest: GenreWatchTimeRequest): Flow<Resource<SimpleResponse>> =
        flow {
            try {
                repository.addWatchTimePair(watchTimeRequest)
            } catch (ignored: Exception) {
            }
        }
}