package eu.bbsapps.forgottenfilmsapp.presentation.auth.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.TextUnit
import eu.bbsapps.forgottenfilmsapp.common.Constants.BIG_SCREEN_THRESHOLD
import eu.bbsapps.forgottenfilmsapp.presentation.ui.theme.*

@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    error: String = "",
    text: String,
    hint: String,
    isHintVisible: Boolean = true,
    onValueChange: (String) -> Unit,
    fontSize: TextUnit = smallFontValue,
    singleLine: Boolean = false,
    onFocusChange: (FocusState) -> Unit,
    isPasswordVisible: Boolean,
    onPasswordVisibilityChanged: (Boolean) -> Unit
) {
    val focusManager = LocalFocusManager.current
    Column(Modifier.fillMaxWidth()) {
        Box(
            contentAlignment = Alignment.CenterStart
        ) {
            OutlinedTextField(
                value = text,
                onValueChange = onValueChange,
                singleLine = singleLine,
                textStyle = TextStyle(fontSize = fontSize),
                modifier = modifier
                    .fillMaxWidth()
                    .onFocusChanged {
                        onFocusChange(it)
                    },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = MaterialTheme.colors.primary,
                    textColor = MaterialTheme.colors.onSurface
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })

            )
            if (isHintVisible) {
                Row {
                    Spacer(modifier = Modifier.width(smallSpacerValue))
                    Text(
                        text = hint,
                        fontSize = fontSize,
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
                    )
                }
            }
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                BoxWithConstraints {
                    val width = maxWidth
                    val iconSize =
                        if (width < BIG_SCREEN_THRESHOLD) smallIconSize else mediumIconSize
                    IconButton({ onPasswordVisibilityChanged(!isPasswordVisible) }) {
                        Icon(
                            if (isPasswordVisible) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility,
                            contentDescription = if (isPasswordVisible) "Password shown" else "Password hidden",
                            tint = MaterialTheme.colors.onSurface,
                            modifier = Modifier.size(iconSize)
                        )
                    }
                }
            }
        }
        if (error.isNotBlank()) {
            Text(text = error, color = Red, fontSize = fontSize)
        }
    }
}