package eu.bbsapps.forgottenfilmsapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import coil.compose.rememberImagePainter
import eu.bbsapps.forgottenfilmsapp.R
import eu.bbsapps.forgottenfilmsapp.common.Constants.BIG_SCREEN_THRESHOLD
import eu.bbsapps.forgottenfilmsapp.data.remote.dto.responses.FilmFeedItem
import eu.bbsapps.forgottenfilmsapp.presentation.ui.theme.mediumFontValue
import eu.bbsapps.forgottenfilmsapp.presentation.ui.theme.mediumSpacerValue
import eu.bbsapps.forgottenfilmsapp.presentation.ui.theme.smallFontValue
import eu.bbsapps.forgottenfilmsapp.presentation.ui.theme.tinyPaddingValue

@Composable
fun FilmListItem(item: FilmFeedItem, onMovieClicked: () -> Unit) {
    BoxWithConstraints(Modifier.fillMaxWidth()) {
        val width = maxWidth
        val imageWidth = (width / 4)
        val imageHeight = imageWidth / 0.66f
        val fontSize = if (width < BIG_SCREEN_THRESHOLD) smallFontValue else mediumFontValue
        Column(
            Modifier
                .fillMaxWidth()
                .clickable { onMovieClicked() }) {
            Divider(color = MaterialTheme.colors.surface)
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(tinyPaddingValue), verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = rememberImagePainter(
                        data = item.image,
                        builder = { error(R.drawable.ic_broken_image) }),
                    contentDescription = null,
                    modifier = Modifier
                        .width(imageWidth)
                        .height(imageHeight)
                )
                Spacer(modifier = Modifier.width(mediumSpacerValue))
                Text(text = item.name, color = MaterialTheme.colors.onSurface, fontSize = fontSize)
            }
            Divider(color = MaterialTheme.colors.surface)
        }
    }
}