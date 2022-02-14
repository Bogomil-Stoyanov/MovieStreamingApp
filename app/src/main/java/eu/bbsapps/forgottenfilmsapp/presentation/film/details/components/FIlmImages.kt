package eu.bbsapps.forgottenfilmsapp.presentation.film.details.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import coil.compose.rememberImagePainter
import eu.bbsapps.forgottenfilmsapp.R
import eu.bbsapps.forgottenfilmsapp.common.Constants.BIG_SCREEN_THRESHOLD
import eu.bbsapps.forgottenfilmsapp.presentation.ui.theme.smallSpacerValue

@Composable
fun FilmImages(imageUrls: List<String>, width: Dp, onImageClicked: (String) -> Unit) {
    val imageWidth = if (width < BIG_SCREEN_THRESHOLD) (width / 1.8f) else (width / 2.4f)
    val imageHeight = imageWidth / 0.66f
    LazyRow {
        items(imageUrls) {
            Image(
                painter = rememberImagePainter(
                    data = it,
                    builder = { error(R.drawable.ic_broken_image) }),
                contentDescription = null,
                modifier = Modifier
                    .width(imageWidth)
                    .height(imageHeight)
                    .clickable { onImageClicked(it) }
            )
            Spacer(modifier = Modifier.width(smallSpacerValue))
        }
    }
}