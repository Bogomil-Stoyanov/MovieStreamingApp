package eu.bbsapps.forgottenfilmsapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import eu.bbsapps.forgottenfilmsapp.presentation.ui.theme.ForgottenFilmsAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ForgottenFilmsAppTheme {

            }
        }
    }
}