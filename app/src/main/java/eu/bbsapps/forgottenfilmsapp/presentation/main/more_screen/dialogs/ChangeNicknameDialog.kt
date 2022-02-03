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
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import eu.bbsapps.forgottenfilmsapp.R
import eu.bbsapps.forgottenfilmsapp.common.Constants
import eu.bbsapps.forgottenfilmsapp.presentation.components.OutlinedTextFieldState
import eu.bbsapps.forgottenfilmsapp.presentation.components.OutlinedTextFieldWithHint
import eu.bbsapps.forgottenfilmsapp.presentation.ui.theme.*

@Composable
fun ChangeNicknameDialog(
    textFieldState: OutlinedTextFieldState,
    onSave: () -> Unit,
    onValueChange: (String) -> Unit,
    onChangeFocus: (FocusState) -> Unit,
    onDismiss: () -> Unit
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
                    text = stringResource(R.string.change_nickname),
                    color = MaterialTheme.colors.onSurface,
                    fontSize = if (width < Constants.BIG_SCREEN_THRESHOLD) smallMediumFontValue else mediumBigFontValue
                )
                Spacer(modifier = Modifier.height(smallSpacerValue))
                OutlinedTextFieldWithHint(
                    modifier = Modifier.clickable {},
                    text = textFieldState.text,
                    hint = stringResource(R.string.new_nickname),
                    onValueChange = { onValueChange(it) },
                    onFocusChange = { onChangeFocus(it) },
                    isHintVisible = textFieldState.isHintVisible,
                    error = textFieldState.error,
                    fontSize = fontSize
                )
                Spacer(modifier = Modifier.height(smallSpacerValue))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    Text(
                        text = stringResource(id = R.string.cancel),
                        fontSize = fontSize,
                        color = MaterialTheme.colors.onSurface,
                        modifier = Modifier.clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { onDismiss() }
                    )
                    Spacer(modifier = Modifier.width(bigSpacerValue))
                    val isSaveButtonEnabled = textFieldState.text.isNotBlank()
                    Text(
                        text = stringResource(id = R.string.save),
                        fontSize = fontSize,
                        color = if (isSaveButtonEnabled) Red else Red.copy(0.4f),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null, enabled = isSaveButtonEnabled
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