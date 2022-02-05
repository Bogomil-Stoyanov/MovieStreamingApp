package eu.bbsapps.forgottenfilmsapp.presentation.admin.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import eu.bbsapps.forgottenfilmsapp.presentation.ui.theme.Red
import eu.bbsapps.forgottenfilmsapp.presentation.ui.theme.mediumPaddingValue
import eu.bbsapps.forgottenfilmsapp.presentation.ui.theme.smallPaddingValue

@Composable
fun StatisticsItem(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    clickable: Boolean = false,
    smallFontSize: TextUnit,
    largeFontSize: TextUnit,
    onClick: () -> Unit = {}
) {
    Column(
        modifier
            .border(width = 2.dp, color = Red, shape = RoundedCornerShape(16.dp))
            .clickable(enabled = clickable) { onClick() }
            .padding(smallPaddingValue),
    ) {
        Text(text = title, color = MaterialTheme.colors.onSurface, fontSize = smallFontSize)
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
            Text(
                text = value,
                color = MaterialTheme.colors.onSurface,
                fontSize = largeFontSize,
                textAlign = TextAlign.End,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(end = mediumPaddingValue)
            )
        }
    }
}