package eu.bbsapps.forgottenfilmsapp.presentation.film.details.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import eu.bbsapps.forgottenfilmsapp.common.Constants.BIG_SCREEN_THRESHOLD
import eu.bbsapps.forgottenfilmsapp.presentation.ui.theme.*

@Composable
fun FilmGenres(genres: List<String>, width: Dp) {
    LazyRow(modifier = Modifier.padding(mediumPaddingValue)) {
        items(genres) {
            FilmGenre(genre = it, width = width)
        }
    }
}

@Composable
fun FilmGenre(genre: String, width: Dp) {
    val fontSize = if (width < BIG_SCREEN_THRESHOLD) smallFontValue else mediumFontValue
    val roundedCornerShapeSize =
        if (width < BIG_SCREEN_THRESHOLD) mediumRoundedCornerValue else bigRoundedCornerValue
    Column(
        Modifier
            .padding(end = smallPaddingValue)
            .border(
                width = 1.dp,
                color = MaterialTheme.colors.primary,
                shape = RoundedCornerShape(roundedCornerShapeSize)
            )
            .padding(horizontal = mediumPaddingValue, vertical = smallPaddingValue),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = genre, color = MaterialTheme.colors.onSurface, fontSize = fontSize)
    }
}