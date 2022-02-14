package eu.bbsapps.forgottenfilmsapp.domain.repository

import eu.bbsapps.forgottenfilmsapp.data.remote.dto.requests.admin.AddFilmsRequest
import eu.bbsapps.forgottenfilmsapp.data.remote.dto.requests.user.login.LoginAccountRequest
import eu.bbsapps.forgottenfilmsapp.data.remote.dto.requests.user.management.GenreWatchTimeRequest
import eu.bbsapps.forgottenfilmsapp.data.remote.dto.requests.user.management.UserGenresRequest
import eu.bbsapps.forgottenfilmsapp.data.remote.dto.requests.user.register.CreateAccountRequest
import eu.bbsapps.forgottenfilmsapp.data.remote.dto.responses.*
import retrofit2.Response

interface FilmRepository {
    suspend fun register(createAccountRequest: CreateAccountRequest): SimpleResponse
    suspend fun login(loginAccountRequest: LoginAccountRequest): SimpleResponse
    suspend fun changeNickname(newNickname: String): SimpleResponse
    suspend fun addInterests(addGenresRequest: UserGenresRequest)
    suspend fun updateInterests(addGenresRequest: UserGenresRequest)
    suspend fun getInterests(): List<String>
    suspend fun addFilmToList(id: String): SimpleResponse
    suspend fun removeFilmFromList(id: String): SimpleResponse
    suspend fun movieLiked(id: String): Int
    suspend fun movieDisliked(id: String): Int
    suspend fun getLikedCount(id: String): Int
    suspend fun getDislikedCount(id: String): Int
    suspend fun getFilm(id: String): FilmResponse
    suspend fun getFeed(): List<FilmFeedResponse>
    suspend fun searchFilms(query: String): List<FilmFeedItem>
    suspend fun isFilmAddedToList(id: String): Boolean
    suspend fun getAllFilmsFromList(): List<FilmFeedItem>
    suspend fun getNickname(): SimpleResponse
    suspend fun getAllFilms(): List<FilmFeedResponse>
    suspend fun getAllGenres(): List<String>
    suspend fun addWatchTimePair(watchTimeRequest: GenreWatchTimeRequest): SimpleResponse
    suspend fun getTotalWatchTime(): List<GenreWatchTimePair>
    suspend fun getFilmsCount(): Int
    suspend fun getUsersCount(): Int
    suspend fun getAdminStats(): List<GenreWatchTimePair>
    suspend fun isAdmin(): Boolean
    suspend fun getAllUsers(): List<UserResponse>
    suspend fun deleteUser(email: String): Response<Unit>
    suspend fun deleteFilm(filmName: String): Response<Unit>
    suspend fun addFilms(addFilmsRequest: AddFilmsRequest): Response<Unit>
    suspend fun forgottenPassword(email: String): SimpleResponse
    suspend fun changePassword(password: String): SimpleResponse
}