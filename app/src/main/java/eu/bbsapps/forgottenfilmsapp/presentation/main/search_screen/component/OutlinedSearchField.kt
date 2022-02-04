package eu.bbsapps.forgottenfilmsapp.presentation.main.search_screen.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import eu.bbsapps.forgottenfilmsapp.R
import eu.bbsapps.forgottenfilmsapp.common.Constants
import eu.bbsapps.forgottenfilmsapp.presentation.ui.theme.mediumIconSize
import eu.bbsapps.forgottenfilmsapp.presentation.ui.theme.smallIconSize

@Composable
fun OutlinedSearchField(
    text: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    textStyle: TextStyle = TextStyle(),
    singleLine: Boolean = false,
    onSearchClicked: () -> Unit
) {
    BoxWithConstraints(
        modifier = modifier, contentAlignment = Alignment.CenterStart
    ) {

        val iconSize =
            if (maxWidth < Constants.BIG_SCREEN_THRESHOLD) smallIconSize else mediumIconSize

        val focusManager = LocalFocusManager.current
        OutlinedTextField(
            value = text,
            onValueChange = onValueChange,
            singleLine = singleLine,
            textStyle = textStyle,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = androidx.compose.ui.text.input.ImeAction.Done),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = MaterialTheme.colors.primary,
                textColor = MaterialTheme.colors.onSurface
            ),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() })
        )
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
            IconButton(onClick = {
                onSearchClicked()
                focusManager.clearFocus()
            }) {
                Icon(
                    modifier = Modifier.size(iconSize),
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(id = R.string.search),
                    tint = MaterialTheme.colors.primary
                )
            }
        }


    }
}