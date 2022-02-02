package eu.bbsapps.forgottenfilmsapp.data.remote

import eu.bbsapps.forgottenfilmsapp.data.remote.dto.requests.admin.AddFilmsRequest
import eu.bbsapps.forgottenfilmsapp.data.remote.dto.requests.user.login.LoginAccountRequest
import eu.bbsapps.forgottenfilmsapp.data.remote.dto.requests.user.management.GenreWatchTimeRequest
import eu.bbsapps.forgottenfilmsapp.data.remote.dto.requests.user.management.UserGenresRequest
import eu.bbsapps.forgottenfilmsapp.data.remote.dto.requests.user.register.CreateAccountRequest
import eu.bbsapps.forgottenfilmsapp.data.remote.dto.responses.*
import retrofit2.Response
import retrofit2.http.*

interface FilmsApi {

    @POST("register")
    suspend fun register(
        @Body createAccountRequest: CreateAccountRequest
    ): SimpleResponse

    @POST("login")
    suspend fun login(
        @Body loginAccountRequest: LoginAccountRequest
    ): SimpleResponse

    @PATCH("nickname")
    suspend fun changeNickname(
        @Query("newNickname") newNickname: String
    ): SimpleResponse

    @POST("interests")
    suspend fun addGenres(
        @Body addGenresRequest: UserGenresRequest
    )

    @GET("interests")
    suspend fun getInterests(): List<String>

    @PATCH("interests")
    suspend fun updateInterests(
        @Body addGenresRequest: UserGenresRequest
    )

    @POST("filmList")
    suspend fun addFilmToList(
        @Query("id") id: String
    ): SimpleResponse

    @DELETE("filmList")
    suspend fun removeFilmFromList(
        @Query("id") id: String
    ): SimpleResponse

    @POST("filmLike")
    suspend fun filmLiked(
        @Query("id") query: String
    ): Int

    @POST("filmDislike")
    suspend fun filmDisliked(
        @Query("id") id: String
    ): Int

    @GET("likes")
    suspend fun getLikedCount(
        @Query("id") id: String
    ): Int

    @GET("dislikes")
    suspend fun getDislikedCount(
        @Query("id") id: String
    ): Int

    @GET("film")
    suspend fun getFilm(
        @Query("id") id: String
    ): FilmResponse

    @GET("feed")
    suspend fun getFeed(): List<FilmFeedResponse>

    @GET("search")
    suspend fun searchFilms(
        @Query("query") query: String
    ): List<FilmFeedItem>

    @GET("filmInList")
    suspend fun isFilmAddedToList(
        @Query("id") id: String
    ): Boolean

    @GET("filmList")
    suspend fun getAllFilmsFromList(): List<FilmFeedItem>

    @GET("nickname")
    suspend fun getNickname(): SimpleResponse

    @GET("films")
    suspend fun getAllFilms(): List<FilmFeedResponse>

    @GET("genres")
    suspend fun getAllGenres(): List<String>

    @POST("watchTime")
    suspend fun addWatchTimePair(
        @Body watchTimeRequest: GenreWatchTimeRequest
    ): SimpleResponse

    @GET("watchTime")
    suspend fun getTotalWatchTime(): List<GenreWatchTimePair>

    @GET("filmsCount")
    suspend fun getFilmsCount(): Int

    @GET("usersCount")
    suspend fun getUsersCount(): Int

    @GET("stats")
    suspend fun getAdminStats(): List<GenreWatchTimePair>

    @GET("isAdmin")
    suspend fun isAdmin(): Boolean

    @GET("users")
    suspend fun getAllUsers(): List<UserResponse>

    @DELETE("user")
    suspend fun deleteUser(
        @Query("email") email: String
    ): Response<Unit>

    @DELETE("films")
    suspend fun deleteFilm(
        @Query("filmName") filmName: String
    ): Response<Unit>

    @POST("films")
    suspend fun addFilms(
        @Body addFilmsRequest: AddFilmsRequest
    ): Response<Unit>


}