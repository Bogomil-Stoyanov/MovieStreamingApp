package eu.bbsapps.forgottenfilmsapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import eu.bbsapps.forgottenfilmsapp.presentation.ui.theme.ForgottenFilmsAppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ForgottenFilmsAppTheme {

            }
        }
    }
}