package eu.bbsapps.forgottenfilmsapp.presentation.main.home_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import coil.compose.rememberImagePainter
import eu.bbsapps.forgottenfilmsapp.R
import eu.bbsapps.forgottenfilmsapp.common.Constants.BIG_SCREEN_THRESHOLD
import eu.bbsapps.forgottenfilmsapp.data.remote.dto.responses.FilmFeedItem
import eu.bbsapps.forgottenfilmsapp.presentation.ui.theme.mediumFontValue
import eu.bbsapps.forgottenfilmsapp.presentation.ui.theme.smallFontValue
import eu.bbsapps.forgottenfilmsapp.presentation.ui.theme.tinyPaddingValue


@Composable
fun FilmFeedRow(title: String, filmList: List<FilmFeedItem>, onMovieClicked: (String) -> Unit) {

    BoxWithConstraints(Modifier.fillMaxWidth()) {
        val width = maxWidth
        val imageWidth = if (width < BIG_SCREEN_THRESHOLD) (width / 2.2f) else (width / 3.2f)
        val imageHeight = imageWidth / 0.66f
        val fontSize = if (width < BIG_SCREEN_THRESHOLD) smallFontValue else mediumFontValue
        Column(Modifier.fillMaxWidth()) {
            Text(text = title, color = MaterialTheme.colors.onSurface, fontSize = fontSize)
            LazyRow {
                items(filmList) { film ->
                    MovieFeedRowItem(
                        imageUrl = film.image,
                        imageWidth = imageWidth,
                        imageHeight = imageHeight
                    ) {
                        onMovieClicked(film.id)
                    }
                }
            }
        }
    }
}

@Composable
fun MovieFeedRowItem(
    imageUrl: String,
    imageWidth: Dp,
    imageHeight: Dp,
    onMovieClicked: () -> Unit
) {
    Image(
        painter = rememberImagePainter(
            data = imageUrl,
            builder = { error(R.drawable.ic_broken_image) }),
        contentDescription = null,
        modifier = Modifier
            .padding(tinyPaddingValue)
            .width(imageWidth)
            .height(imageHeight)
            .clickable {
                onMovieClicked()
            }
    )
}