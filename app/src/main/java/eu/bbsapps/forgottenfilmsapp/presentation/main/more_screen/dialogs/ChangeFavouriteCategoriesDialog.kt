package eu.bbsapps.forgottenfilmsapp.presentation.main.more_screen.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eu.bbsapps.forgottenfilmsapp.R
import eu.bbsapps.forgottenfilmsapp.common.Constants
import eu.bbsapps.forgottenfilmsapp.presentation.main.more_screen.components.GenresMoreSelection
import eu.bbsapps.forgottenfilmsapp.presentation.ui.theme.*

@Composable
fun ChangeFavouriteCategoriesDialog(
    genres: List<String>,
    selectedCategories: List<String>,
    onDismiss: () -> Unit,
    onCheck: (String) -> Unit,
    onSave: () -> Unit
) {
    BoxWithConstraints(
        modifier = Modifier
            .background(MaterialTheme.colors.surface.copy(0.8f))
            .fillMaxSize()
            .clickable { onDismiss() }
            .padding(largePaddingValue), contentAlignment = Alignment.Center
    ) {
        val width = maxWidth
        val fontSize = if (width < Constants.BIG_SCREEN_THRESHOLD) 18.sp else 26.sp

        Box(
            Modifier
                .clip(RoundedCornerShape(smallRoundedCornerValue))
                .background(MaterialTheme.colors.background)
                .clickable(enabled = false) { }
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(bigPaddingValue)
            ) {
                Text(
                    text = stringResource(R.string.favourite_genres),
                    color = MaterialTheme.colors.onSurface,
                    fontSize = if (width < Constants.BIG_SCREEN_THRESHOLD) smallMediumFontValue else mediumBigFontValue
                )
                Spacer(modifier = Modifier.height(smallSpacerValue))
                GenresMoreSelection(
                    currentlySelected = selectedCategories,
                    onCheck = { onCheck(it) },
                    genres = genres,
                    fontSize = fontSize,
                    height = if (width < Constants.BIG_SCREEN_THRESHOLD) 250.dp else 350.dp
                )
                Spacer(modifier = Modifier.height(smallSpacerValue))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    Text(
                        text = stringResource(R.string.cancel),
                        color = MaterialTheme.colors.onSurface,
                        fontSize = fontSize,
                        modifier = Modifier.clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { onDismiss() }
                    )
                    Spacer(modifier = Modifier.width(bigSpacerValue))
                    Text(
                        text = stringResource(R.string.save),
                        color = if (selectedCategories.size >= 3) Red else Red.copy(0.4f),
                        fontSize = fontSize,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            enabled = selectedCategories.size >= 3
                        ) {
                            onSave()
                            onDismiss()
                        }
                    )
                }
            }
        }
    }
}