package eu.bbsapps.forgottenfilmsapp.di

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import eu.bbsapps.forgottenfilmsapp.common.Constants.BASE_URL
import eu.bbsapps.forgottenfilmsapp.common.Constants.ENCRYPTED_SHARED_PREF_NAME
import eu.bbsapps.forgottenfilmsapp.data.remote.BasicAuthInterceptor
import eu.bbsapps.forgottenfilmsapp.data.remote.FilmsApi
import eu.bbsapps.forgottenfilmsapp.data.repository.FilmRepositoryImpl
import eu.bbsapps.forgottenfilmsapp.domain.repository.FilmRepository
import eu.bbsapps.forgottenfilmsapp.domain.use_case.film.movielist.AddFilmToListUseCase
import eu.bbsapps.forgottenfilmsapp.domain.use_case.film.movielist.FilmListUseCases
import eu.bbsapps.forgottenfilmsapp.domain.use_case.film.movielist.IsFilmAddedToListUseCase
import eu.bbsapps.forgottenfilmsapp.domain.use_case.film.movielist.RemoveFilmFromListUseCase
import eu.bbsapps.forgottenfilmsapp.domain.use_case.film.rating.*
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.inject.Singleton
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideMovieRepository(filmsApi: FilmsApi): FilmRepository =
        FilmRepositoryImpl(filmsApi)

    @Singleton
    @Provides
    fun provideMovieApi(
        okHttpClient: OkHttpClient.Builder,
        basicAuthInterceptor: BasicAuthInterceptor
    ): FilmsApi {
        val client = okHttpClient.addInterceptor(basicAuthInterceptor).build()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client).build()
            .create(FilmsApi::class.java)

    }

    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient.Builder {
        val trustAllCertificates: Array<TrustManager> = arrayOf(
            @SuppressLint("CustomX509TrustManager")
            object : X509TrustManager {
                @SuppressLint("TrustAllX509TrustManager")
                override fun checkClientTrusted(p0: Array<out X509Certificate>?, p1: String?) {
                    //NO-OP
                }

                @SuppressLint("TrustAllX509TrustManager")
                override fun checkServerTrusted(p0: Array<out X509Certificate>?, p1: String?) {
                    //NO-OP
                }

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return emptyArray()
                }
            }
        )
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCertificates, SecureRandom())
        return OkHttpClient.Builder()
            .sslSocketFactory(sslContext.socketFactory, trustAllCertificates[0] as X509TrustManager)
            .hostnameVerifier(HostnameVerifier { _, _ -> true })
    }

    @Singleton
    @Provides
    fun provideEncryptedSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        val masterKey =
            MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()
        return EncryptedSharedPreferences.create(
            context,
            ENCRYPTED_SHARED_PREF_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    @Singleton
    @Provides
    fun provideFilmRatingUseCases(repository: FilmRepository) = FilmRatingUseCases(
        filmLikedUseCase = FilmLikedUseCase(repository),
        filmDislikedUseCase = FilmDislikedUseCase(repository),
        filmLikesCountUseCase = FilmLikesCountUseCase(repository),
        filmDislikesCountUseCase = FilmDislikesCountUseCase(repository)
    )

    @Singleton
    @Provides
    fun provideFilmListUseCases(repository: FilmRepository) = FilmListUseCases(
        addFilmToListUseCase = AddFilmToListUseCase(repository),
        removeFilmFromListUseCase = RemoveFilmFromListUseCase(repository),
        isFilmAddedToListUseCase = IsFilmAddedToListUseCase(repository)
    )

    @Singleton
    @Provides
    fun provideBasicAuthInterceptor() = BasicAuthInterceptor()
}