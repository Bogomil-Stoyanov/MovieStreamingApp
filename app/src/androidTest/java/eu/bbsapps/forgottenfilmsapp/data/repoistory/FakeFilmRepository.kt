package eu.bbsapps.forgottenfilmsapp.data.repoistory

import eu.bbsapps.forgottenfilmsapp.data.remote.dto.requests.admin.AddFilmsRequest
import eu.bbsapps.forgottenfilmsapp.data.remote.dto.requests.user.login.LoginAccountRequest
import eu.bbsapps.forgottenfilmsapp.data.remote.dto.requests.user.management.GenreWatchTimeRequest
import eu.bbsapps.forgottenfilmsapp.data.remote.dto.requests.user.management.UserGenresRequest
import eu.bbsapps.forgottenfilmsapp.data.remote.dto.requests.user.register.CreateAccountRequest
import eu.bbsapps.forgottenfilmsapp.data.remote.dto.responses.*
import eu.bbsapps.forgottenfilmsapp.domain.repository.FilmRepository
import retrofit2.Response

class FakeFilmRepository : FilmRepository {

    override suspend fun register(createAccountRequest: CreateAccountRequest): SimpleResponse {
        return SimpleResponse(true, "Successfully created account")
    }

    override suspend fun login(loginAccountRequest: LoginAccountRequest): SimpleResponse {
        return SimpleResponse(true, "You are now logged in")
    }

    override suspend fun changeNickname(newNickname: String): SimpleResponse {
        return SimpleResponse(true, "Successfully changed your nickname")
    }

    override suspend fun addInterests(addGenresRequest: UserGenresRequest) {
    }

    override suspend fun updateInterests(addGenresRequest: UserGenresRequest) {
    }

    override suspend fun getInterests(): List<String> {
        return listOf("Action", "Documentary")
    }

    override suspend fun addFilmToList(id: String): SimpleResponse {
        return SimpleResponse(true, "Film added to your list")
    }

    override suspend fun removeFilmFromList(id: String): SimpleResponse {
        return SimpleResponse(true, "Film removed from your list")
    }

    override suspend fun movieLiked(id: String): Int {
        return 1
    }

    override suspend fun movieDisliked(id: String): Int {
        return -1
    }

    override suspend fun getLikedCount(id: String): Int {
        return 100
    }

    override suspend fun getDislikedCount(id: String): Int {
        return 100
    }

    override suspend fun getFilm(id: String): FilmResponse {
        return FilmResponse(
            "name",
            listOf("imageUrl"),
            "description",
            listOf("action"),
            likes = 10,
            dislikes = 0,
            isLiked = 0,
            url = "url",
            id = "id"
        )
    }

    override suspend fun getFeed(): List<FilmFeedResponse> {
        return listOf(
            FilmFeedResponse(
                "Action",
                listOf(FilmFeedItem("Action", "image1", "id1"))
            ),
            FilmFeedResponse(
                "Comedy",
                listOf(FilmFeedItem("Comedy", "image2", "id2"))
            ),
        )
    }

    override suspend fun searchFilms(query: String): List<FilmFeedItem> {
        return listOf(
            FilmFeedItem("Film {$query}1", "image1", "id1"),
            FilmFeedItem("Film {$query}2", "image2", "id2")
        )
    }

    override suspend fun isFilmAddedToList(id: String): Boolean {
        return false
    }

    override suspend fun getAllFilmsFromList(): List<FilmFeedItem> {
        return listOf(
            FilmFeedItem("Film 1", "image1", "id1"),
            FilmFeedItem("Film 2", "image2", "id2")
        )
    }

    override suspend fun getNickname(): SimpleResponse {
        return SimpleResponse(true, "Nickname")
    }

    override suspend fun getAllFilms(): List<FilmFeedResponse> {
        return emptyList()
    }

    override suspend fun getAllGenres(): List<String> {
        return genres
    }

    override suspend fun addWatchTimePair(watchTimeRequest: GenreWatchTimeRequest): SimpleResponse {
        return SimpleResponse(true, "Успешно обновено време за гледане")
    }

    override suspend fun getTotalWatchTime(): List<GenreWatchTimePair> {
        return listOf(
            GenreWatchTimePair("Comedy", 1000),
            GenreWatchTimePair("Action", 200),
            GenreWatchTimePair("Horror", 2000)
        )
    }

    override suspend fun getFilmsCount(): Int {
        return 20
    }

    override suspend fun getUsersCount(): Int {
        return 20
    }

    override suspend fun getAdminStats(): List<GenreWatchTimePair> {
        return listOf(
            GenreWatchTimePair("Comedy", 10000),
            GenreWatchTimePair("Action", 800),
            GenreWatchTimePair("Horror", 2500)
        )
    }

    override suspend fun isAdmin(): Boolean {
        return false
    }

    override suspend fun getAllUsers(): List<UserResponse> {
        return listOf(
            UserResponse("email1", "Name1"),
            UserResponse("email2", "Name2"),
            UserResponse("email2", "Name2"),
        )
    }

    override suspend fun deleteUser(email: String): Response<Unit> {
        return Response.success(Unit)
    }

    override suspend fun deleteFilm(filmName: String): Response<Unit> {
        return Response.success(Unit)
    }

    override suspend fun addFilms(addFilmsRequest: AddFilmsRequest): Response<Unit> {
        return Response.success(Unit)
    }
}

val genres = listOf("Animation", "Action", "Drama", "Horror")