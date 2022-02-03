package eu.bbsapps.forgottenfilmsapp.data.remote

import eu.bbsapps.forgottenfilmsapp.common.Constants.ACCOUNT_MANAGEMENT_API_KEY
import eu.bbsapps.forgottenfilmsapp.common.Constants.ADMIN_API_KEY
import eu.bbsapps.forgottenfilmsapp.common.Constants.FILMS_API_KEY
import eu.bbsapps.forgottenfilmsapp.common.Constants.LOGIN_API_KEY
import eu.bbsapps.forgottenfilmsapp.common.Constants.REGISTER_API_KEY
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
        @Body createAccountRequest: CreateAccountRequest,
        @Query("apiKey") apiKey: String = REGISTER_API_KEY
    ): SimpleResponse

    @POST("login")
    suspend fun login(
        @Body loginAccountRequest: LoginAccountRequest,
        @Query("apiKey") apiKey: String = LOGIN_API_KEY
    ): SimpleResponse

    @PATCH("nickname")
    suspend fun changeNickname(
        @Query("newNickname") newNickname: String,
        @Query("apiKey") apiKey: String = ACCOUNT_MANAGEMENT_API_KEY
    ): SimpleResponse

    @POST("genres")
    suspend fun addGenres(
        @Body addGenresRequest: UserGenresRequest,
        @Query("apiKey") apiKey: String = ACCOUNT_MANAGEMENT_API_KEY
    )

    @GET("userGenres")
    suspend fun getUserGenres(
        @Query("apiKey") apiKey: String = ACCOUNT_MANAGEMENT_API_KEY
    ): List<String>

    @PATCH("genres")
    suspend fun updateGenres(
        @Body addGenresRequest: UserGenresRequest,
        @Query("apiKey") apiKey: String = ACCOUNT_MANAGEMENT_API_KEY,
    )

    @POST("filmList")
    suspend fun addFilmToList(
        @Query("id") id: String,
        @Query("apiKey") apiKey: String = ACCOUNT_MANAGEMENT_API_KEY,
    ): SimpleResponse

    @DELETE("filmList")
    suspend fun removeFilmFromList(
        @Query("id") id: String,
        @Query("apiKey") apiKey: String = ACCOUNT_MANAGEMENT_API_KEY
    ): SimpleResponse

    @GET("nickname")
    suspend fun getNickname(
        @Query("apiKey") apiKey: String = ACCOUNT_MANAGEMENT_API_KEY
    ): SimpleResponse

    @GET("filmList")
    suspend fun getAllFilmsFromList(
        @Query("apiKey") apiKey: String = ACCOUNT_MANAGEMENT_API_KEY
    ): List<FilmFeedItem>

    @POST("watchTime")
    suspend fun addWatchTimePair(
        @Body watchTimeRequest: GenreWatchTimeRequest,
        @Query("apiKey") apiKey: String = ACCOUNT_MANAGEMENT_API_KEY
    ): SimpleResponse

    @GET("watchTime")
    suspend fun getTotalWatchTime(
        @Query("apiKey") apiKey: String = ACCOUNT_MANAGEMENT_API_KEY
    ): List<GenreWatchTimePair>

    @POST("filmLike")
    suspend fun filmLiked(
        @Query("id") query: String,
        @Query("apiKey") apiKey: String = FILMS_API_KEY
    ): Int

    @POST("filmDislike")
    suspend fun filmDisliked(
        @Query("id") id: String,
        @Query("apiKey") apiKey: String = FILMS_API_KEY
    ): Int

    @GET("likes")
    suspend fun getLikedCount(
        @Query("id") id: String,
        @Query("apiKey") apiKey: String = FILMS_API_KEY
    ): Int

    @GET("dislikes")
    suspend fun getDislikedCount(
        @Query("id") id: String,
        @Query("apiKey") apiKey: String = FILMS_API_KEY
    ): Int

    @GET("film")
    suspend fun getFilm(
        @Query("id") id: String,
        @Query("apiKey") apiKey: String = FILMS_API_KEY
    ): FilmResponse

    @GET("feed")
    suspend fun getFeed(
        @Query("apiKey") apiKey: String = FILMS_API_KEY
    ): List<FilmFeedResponse>

    @GET("search")
    suspend fun searchFilms(
        @Query("query") query: String,
        @Query("apiKey") apiKey: String = FILMS_API_KEY
    ): List<FilmFeedItem>

    @GET("filmInList")
    suspend fun isFilmAddedToList(
        @Query("id") id: String,
        @Query("apiKey") apiKey: String = FILMS_API_KEY
    ): Boolean

    @GET("films")
    suspend fun getAllFilms(
        @Query("apiKey") apiKey: String = FILMS_API_KEY
    ): List<FilmFeedResponse>

    @GET("genres")
    suspend fun getAllGenres(
        @Query("apiKey") apiKey: String = FILMS_API_KEY
    ): List<String>

    @GET("filmsCount")
    suspend fun getFilmsCount(
        @Query("apiKey") apiKey: String = ADMIN_API_KEY
    ): Int

    @GET("usersCount")
    suspend fun getUsersCount(
        @Query("apiKey") apiKey: String = ADMIN_API_KEY
    ): Int

    @GET("stats")
    suspend fun getAdminStats(
        @Query("apiKey") apiKey: String = ADMIN_API_KEY
    ): List<GenreWatchTimePair>

    @GET("isAdmin")
    suspend fun isAdmin(
        @Query("apiKey") apiKey: String = ACCOUNT_MANAGEMENT_API_KEY
    ): Boolean

    @GET("users")
    suspend fun getAllUsers(
        @Query("apiKey") apiKey: String = ADMIN_API_KEY
    ): List<UserResponse>

    @DELETE("user")
    suspend fun deleteUser(
        @Query("email") email: String,
        @Query("apiKey") apiKey: String = ADMIN_API_KEY
    ): Response<Unit>

    @DELETE("films")
    suspend fun deleteFilm(
        @Query("filmName") filmName: String,
        @Query("apiKey") apiKey: String = ADMIN_API_KEY
    ): Response<Unit>

    @POST("films")
    suspend fun addFilms(
        @Body addFilmsRequest: AddFilmsRequest,
        @Query("apiKey") apiKey: String = ADMIN_API_KEY
    ): Response<Unit>


}