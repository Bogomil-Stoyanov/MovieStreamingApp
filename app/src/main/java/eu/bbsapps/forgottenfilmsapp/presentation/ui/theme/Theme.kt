package eu.bbsapps.forgottenfilmsapp.presentation.ui.theme

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.LocalOverScrollConfiguration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color

private val LightColorPalette = lightColors(
    primary = Red,
    background = BackgroundLight,
    surface = Color.White,
    onSurface = Color.Black
)

private val DarkColorPalette = lightColors(
    primary = Red,
    background = BackgroundDark,
    surface = Color.Black,
    onSurface = Color.White
)


@ExperimentalFoundationApi
@Composable
fun ForgottenFilmsAppTheme(
    content: @Composable() () -> Unit
) {
    val colors = if (isSystemInDarkTheme()) DarkColorPalette else LightColorPalette

    MaterialTheme(
        colors = colors,
        shapes = Shapes,
    ) {
        CompositionLocalProvider(
            LocalOverScrollConfiguration provides null,
            content = content
        )
    }
}