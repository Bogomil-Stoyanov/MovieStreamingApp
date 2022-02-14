package eu.bbsapps.forgottenfilmsapp.presentation.main.more_screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import eu.bbsapps.forgottenfilmsapp.presentation.ui.theme.tinyPaddingValue
import eu.bbsapps.forgottenfilmsapp.presentation.ui.theme.tinySpacerValue

@Composable
fun GenresMoreSelection(
    genres: List<String>,
    currentlySelected: List<String>,
    fontSize: TextUnit,
    height: Dp = 200.dp,
    onCheck: (String) -> Unit,
) {
    LazyColumn(Modifier.height(height = height), horizontalAlignment = Alignment.Start) {
        items(genres) { category ->
            GenreMoreItem(
                text = category,
                isSelected = currentlySelected.contains(category),
                fontSize = fontSize,
                onCheck = { onCheck(category) }
            )
        }
    }
}

@Composable
fun GenreMoreItem(
    text: String,
    isSelected: Boolean,
    fontSize: TextUnit,
    onCheck: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(tinyPaddingValue)
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
        Text(text = text, fontSize = fontSize, color = MaterialTheme.colors.onSurface)
    }
}