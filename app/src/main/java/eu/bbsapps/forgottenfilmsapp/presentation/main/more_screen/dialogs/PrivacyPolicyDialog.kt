package eu.bbsapps.forgottenfilmsapp.presentation.main.more_screen.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eu.bbsapps.forgottenfilmsapp.R
import eu.bbsapps.forgottenfilmsapp.common.Constants
import eu.bbsapps.forgottenfilmsapp.presentation.ui.theme.*

@Composable
fun PrivacyPolicyDialog(onDismiss: () -> Unit) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.surface.copy(0.8f))
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
                modifier = Modifier.padding(bigPaddingValue)
            ) {
                Text(
                    text = stringResource(id = R.string.privacy_policy),
                    color = MaterialTheme.colors.onSurface,
                    fontSize = if (width < Constants.BIG_SCREEN_THRESHOLD) smallMediumFontValue else mediumBigFontValue
                )
                Spacer(modifier = Modifier.height(smallSpacerValue))
                LazyColumn(
                    Modifier
                        .height(if (width < Constants.BIG_SCREEN_THRESHOLD) 300.dp else 400.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    item {

                        Text(
                            text = stringResource(id = R.string.privacy_policy_content),
                            color = MaterialTheme.colors.onSurface,
                            fontSize = fontSize
                        )
                    }
                }
                Spacer(modifier = Modifier.height(smallSpacerValue))
                Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = stringResource(id = R.string.cancel),
                        color = Color.Red,
                        fontSize = fontSize,
                        modifier = Modifier.clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { onDismiss() }
                    )
                }
            }
        }
    }
}