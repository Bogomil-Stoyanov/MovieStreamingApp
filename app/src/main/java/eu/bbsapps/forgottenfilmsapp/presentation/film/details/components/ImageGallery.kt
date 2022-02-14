package eu.bbsapps.forgottenfilmsapp.presentation.film.details.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberImagePainter
import eu.bbsapps.forgottenfilmsapp.R
import eu.bbsapps.forgottenfilmsapp.common.Constants.BIG_SCREEN_THRESHOLD
import eu.bbsapps.forgottenfilmsapp.presentation.ui.theme.bigIconSize
import eu.bbsapps.forgottenfilmsapp.presentation.ui.theme.mediumIconSize

@Composable
fun ImageGallery(
    onNextClicked: () -> Unit,
    onPreviousClicked: () -> Unit,
    onClose: () -> Unit,
    currentImageUrl: String
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.surface.copy(0.8f))
            .clickable { onClose() }, contentAlignment = Alignment.Center
    ) {
        val width = maxWidth
        val imageWidth = width / 1.3f
        val iconSize = if (width < BIG_SCREEN_THRESHOLD) mediumIconSize else bigIconSize

        Row(
            Modifier
                .clickable(false) {},
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            IconButton(onClick = {
                onPreviousClicked()
            }) {
                Icon(
                    imageVector = Icons.Default.ChevronLeft,
                    contentDescription = "",
                    tint = MaterialTheme.colors.onSurface,
                    modifier = Modifier.size(iconSize)
                )
            }
            Image(
                painter = rememberImagePainter(
                    data = currentImageUrl,
                    builder = {
                        error(R.drawable.ic_broken_image)
                    },
                ),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.width(imageWidth)
            )
            IconButton(
                onClick = { onNextClicked() }
            ) {
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = "",
                    tint = MaterialTheme.colors.onSurface,
                    modifier = Modifier.size(iconSize)
                )
            }
        }
    }
}