package eu.bbsapps.forgottenfilmsapp.presentation.auth.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import com.google.accompanist.flowlayout.FlowRow
import eu.bbsapps.forgottenfilmsapp.presentation.ui.theme.tinySpacerValue

@Composable
fun GenresRegisterSelection(
    currentlySelected: List<String>,
    onCheck: (String) -> Unit,
    fontSize: TextUnit,
    genres: List<String>
) {
    FlowRow() {
        for (category in genres) {
            GenreRegisterItem(
                text = category,
                isSelected = currentlySelected.contains(category),
                onCheck = { onCheck(category) },
                fontSize = fontSize
            )
        }
    }
}

@Composable
fun GenreRegisterItem(
    text: String,
    isSelected: Boolean,
    onCheck: (String) -> Unit,
    fontSize: TextUnit
) {
    Row(
        modifier = Modifier
            .padding(tinySpacerValue)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onCheck(text) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isSelected,
            onCheckedChange = null,
            colors = CheckboxDefaults.colors(
                uncheckedColor = MaterialTheme.colors.onSurface.copy(alpha = 0.7f),
                checkedColor = MaterialTheme.colors.primary
            ),
        )
        Spacer(modifier = Modifier.width(tinySpacerValue))
        Text(text = text, color = MaterialTheme.colors.onSurface, fontSize = fontSize)
    }
}