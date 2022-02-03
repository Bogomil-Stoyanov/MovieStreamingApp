package eu.bbsapps.forgottenfilmsapp.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import eu.bbsapps.forgottenfilmsapp.presentation.ui.theme.Red
import eu.bbsapps.forgottenfilmsapp.presentation.ui.theme.smallSpacerValue

@Composable
fun OutlinedTextFieldWithHint(
    modifier: Modifier = Modifier,
    error: String = "",
    text: String,
    hint: String,
    isHintVisible: Boolean = true,
    onValueChange: (String) -> Unit,
    fontSize: TextUnit = 16.sp,
    singleLine: Boolean = false,
    onFocusChange: (FocusState) -> Unit,
    isEmail: Boolean = false
) {
    Column {
        Box(
            contentAlignment = Alignment.CenterStart
        ) {
            val focusManager = LocalFocusManager.current
            OutlinedTextField(
                value = text,
                onValueChange = onValueChange,
                singleLine = singleLine,
                modifier = modifier
                    .fillMaxWidth()
                    .onFocusChanged {
                        onFocusChange(it)
                    },
                textStyle = TextStyle(fontSize = fontSize),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = MaterialTheme.colors.primary,
                    textColor = MaterialTheme.colors.onSurface
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = if (isEmail) KeyboardType.Email else KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }
                )
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
        }
        if (error.isNotBlank()) {
            Text(text = error, color = Red, fontSize = fontSize)
        }
    }
}