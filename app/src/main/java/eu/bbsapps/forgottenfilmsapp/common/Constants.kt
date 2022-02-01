package eu.bbsapps.forgottenfilmsapp.common

import androidx.compose.ui.unit.dp

object Constants {
    const val BASE_URL = "https://10.0.2.2:8081/v1/"
    //const val BASE_URL = "https://157.230.22.117:8081/v1/"

    val IGNORE_AUTH_URLS = listOf("/login", "/register")

    const val REGISTER_API_KEY = ""
    const val ADMIN_API_KEY = ""

    const val ENCRYPTED_SHARED_PREF_NAME = "enc_shared_pref"
    const val KEY_LOGGED_IN_EMAIL = "KEY_LOGGED_IN_EMAIL"
    const val KEY_PASSWORD = "KEY_PASSWORD"
    const val NO_EMAIL = "NO_EMAIL"
    const val NO_PASSWORD = "NO_PASSWORD"

    const val SPLASH_SCREEN_DURATION = 2000L

    val BIG_SCREEN_THRESHOLD = 500.dp

}