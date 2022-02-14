package eu.bbsapps.forgottenfilmsapp.presentation.film.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import eu.bbsapps.forgottenfilmsapp.R
import eu.bbsapps.forgottenfilmsapp.common.Constants.BIG_SCREEN_THRESHOLD
import eu.bbsapps.forgottenfilmsapp.presentation.ui.theme.bigIconSize
import eu.bbsapps.forgottenfilmsapp.presentation.ui.theme.mediumPaddingValue
import eu.bbsapps.forgottenfilmsapp.presentation.ui.theme.smallIconSize

@Composable
fun FilmDetailsTopBar(onNavigateUp: () -> Unit) {
    BoxWithConstraints() {
        val width = maxWidth
        val iconSize = if (width < BIG_SCREEN_THRESHOLD) smallIconSize else bigIconSize
        val padding = if (width < BIG_SCREEN_THRESHOLD) 0.dp else mediumPaddingValue
        Row(
            Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.surface)
                .padding(padding)
        ) {
            IconButton(onClick = { onNavigateUp() }) {
                Icon(
                    modifier = Modifier.size(iconSize),
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                    tint = MaterialTheme.colors.onSurface
                )
            }
        }
    }
}