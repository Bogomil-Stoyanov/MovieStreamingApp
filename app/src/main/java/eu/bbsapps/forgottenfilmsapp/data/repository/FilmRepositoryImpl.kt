package eu.bbsapps.forgottenfilmsapp.data.repository

import eu.bbsapps.forgottenfilmsapp.data.remote.FilmsApi
import eu.bbsapps.forgottenfilmsapp.data.remote.dto.requests.admin.AddFilmsRequest
import eu.bbsapps.forgottenfilmsapp.data.remote.dto.requests.user.login.LoginAccountRequest
import eu.bbsapps.forgottenfilmsapp.data.remote.dto.requests.user.management.GenreWatchTimeRequest
import eu.bbsapps.forgottenfilmsapp.data.remote.dto.requests.user.management.UserGenresRequest
import eu.bbsapps.forgottenfilmsapp.data.remote.dto.requests.user.register.CreateAccountRequest
import eu.bbsapps.forgottenfilmsapp.data.remote.dto.responses.*
import eu.bbsapps.forgottenfilmsapp.domain.repository.FilmRepository
import retrofit2.Response
import javax.inject.Inject

class FilmRepositoryImpl @Inject constructor(private val moviesApi: FilmsApi) : FilmRepository {

    override suspend fun register(createAccountRequest: CreateAccountRequest): SimpleResponse {
        return moviesApi.register(createAccountRequest)
    }

    override suspend fun login(loginAccountRequest: LoginAccountRequest): SimpleResponse {
        return moviesApi.login(loginAccountRequest)
    }

    override suspend fun changeNickname(newNickname: String): SimpleResponse {
        return moviesApi.changeNickname(newNickname)
    }

    override suspend fun addInterests(addGenresRequest: UserGenresRequest) {
        moviesApi.addGenres(addGenresRequest)
    }

    override suspend fun updateInterests(addGenresRequest: UserGenresRequest) {
        moviesApi.updateGenres(addGenresRequest)
    }

    override suspend fun getInterests(): List<String> {
        return moviesApi.getUserGenres()
    }

    override suspend fun addFilmToList(id: String): SimpleResponse {
        return moviesApi.addFilmToList(id)
    }

    override suspend fun removeFilmFromList(id: String): SimpleResponse {
        return moviesApi.removeFilmFromList(id)
    }

    override suspend fun movieLiked(id: String): Int {
        return moviesApi.filmLiked(id)
    }

    override suspend fun movieDisliked(id: String): Int {
        return moviesApi.filmDisliked(id)
    }

    override suspend fun getLikedCount(id: String): Int {
        return moviesApi.getLikedCount(id)
    }

    override suspend fun getDislikedCount(id: String): Int {
        return moviesApi.getDislikedCount(id)
    }

    override suspend fun getFilm(id: String): FilmResponse {
        return moviesApi.getFilm(id)
    }

    override suspend fun getFeed(): List<FilmFeedResponse> {
        return moviesApi.getFeed()
    }

    override suspend fun searchFilms(query: String): List<FilmFeedItem> {
        return moviesApi.searchFilms(query)
    }

    override suspend fun isFilmAddedToList(id: String): Boolean {
        return moviesApi.isFilmAddedToList(id)
    }

    override suspend fun getAllFilmsFromList(): List<FilmFeedItem> {
        return moviesApi.getAllFilmsFromList()
    }

    override suspend fun getNickname(): SimpleResponse {
        return moviesApi.getNickname()
    }

    override suspend fun getAllFilms(): List<FilmFeedResponse> {
        return moviesApi.getAllFilms()
    }

    override suspend fun getAllGenres(): List<String> {
        return moviesApi.getAllGenres()
    }

    override suspend fun addWatchTimePair(watchTimeRequest: GenreWatchTimeRequest): SimpleResponse {
        return moviesApi.addWatchTimePair(watchTimeRequest)
    }

    override suspend fun getTotalWatchTime(): List<GenreWatchTimePair> {
        return moviesApi.getTotalWatchTime()
    }

    override suspend fun getFilmsCount(): Int {
        return moviesApi.getFilmsCount()
    }

    override suspend fun getUsersCount(): Int {
        return moviesApi.getUsersCount()
    }

    override suspend fun getAdminStats(): List<GenreWatchTimePair> {
        return moviesApi.getAdminStats()
    }

    override suspend fun isAdmin(): Boolean {
        return moviesApi.isAdmin()
    }

    override suspend fun getAllUsers(): List<UserResponse> {
        return moviesApi.getAllUsers()
    }

    override suspend fun deleteUser(email: String): Response<Unit> {
        return moviesApi.deleteUser(email)
    }

    override suspend fun deleteFilm(filmName: String): Response<Unit> {
        return moviesApi.deleteFilm(filmName)
    }

    override suspend fun addFilms(addFilmsRequest: AddFilmsRequest): Response<Unit> {
        return moviesApi.addFilms(addFilmsRequest)
    }

    override suspend fun forgottenPassword(email: String): SimpleResponse {
        return moviesApi.forgottenPassword(email)
    }

    override suspend fun changePassword(password: String): SimpleResponse {
        return moviesApi.changePassword(password)
    }
}