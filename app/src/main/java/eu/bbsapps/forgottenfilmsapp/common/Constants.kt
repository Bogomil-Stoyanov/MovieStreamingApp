package eu.bbsapps.forgottenfilmsapp.common

import androidx.compose.ui.unit.dp

object Constants {
    //const val BASE_URL = "https://10.0.2.2:8081/v1/"
    const val BASE_URL = "https://157.230.22.117:8081/v1/"

    val IGNORE_AUTH_URLS = listOf("/login", "/register")

    const val REGISTER_API_KEY = "f8c4a066-5837-416e-886b-ca0b2beac190"
    const val LOGIN_API_KEY = "04eed1b4-8a45-4c7c-b0da-51acb192bb10"
    const val ACCOUNT_MANAGEMENT_API_KEY = "d0a43657-7a22-43ee-87b3-474ee6c41497"
    const val ADMIN_API_KEY = "7a8388ad-eafd-4517-ad47-626de6499fce"
    const val FILMS_API_KEY = "6ad31ae3-34be-4481-9e43-27461b8adfb8"

    const val ENCRYPTED_SHARED_PREF_NAME = "enc_shared_pref"
    const val KEY_LOGGED_IN_EMAIL = "KEY_LOGGED_IN_EMAIL"
    const val KEY_PASSWORD = "KEY_PASSWORD"
    const val NO_EMAIL = "NO_EMAIL"
    const val NO_PASSWORD = "NO_PASSWORD"

    const val SPLASH_SCREEN_DURATION = 2000L

    val BIG_SCREEN_THRESHOLD = 500.dp
}