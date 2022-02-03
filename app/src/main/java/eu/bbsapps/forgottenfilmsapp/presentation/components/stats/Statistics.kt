package eu.bbsapps.forgottenfilmsapp.presentation.components.stats

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import eu.bbsapps.forgottenfilmsapp.common.Constants
import eu.bbsapps.forgottenfilmsapp.data.remote.dto.responses.GenreWatchTimePair
import eu.bbsapps.forgottenfilmsapp.presentation.ui.theme.*

@Composable
fun Statistics(watchTimeStats: List<GenreWatchTimePair>) {
    if (watchTimeStats.size == 1) {
        BoxWithConstraints(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            val width = maxWidth
            Text(
                text = "Статистики все още не се налични",
                color = MaterialTheme.colors.onSurface,
                fontSize = if (width < Constants.BIG_SCREEN_THRESHOLD)
                    smallFontValue else mediumFontValue
            )
        }
    } else {
        BoxWithConstraints(Modifier.fillMaxWidth()) {
            val width = maxWidth

            val fontSize =
                if (width < Constants.BIG_SCREEN_THRESHOLD) smallFontValue else mediumFontValue

            Column(Modifier.fillMaxWidth()) {

                var activePiePart by remember {
                    mutableStateOf(-1)
                }

                Text(
                    text = "Обща продължителност на гледане: ${
                        getFormattedTimeFromSeconds(
                            watchTimeStats[0].totalWatchTimeInSeconds
                        )
                    }",
                    color = MaterialTheme.colors.onSurface,
                    fontSize = fontSize
                )
                Divider()
                Row(Modifier.fillMaxWidth()) {
                    val mainColor = Red
                    val colorsSaturation = remember {
                        mutableListOf<Color>()
                    }
                    val colorStep = remember {
                        mutableStateOf(90 / (watchTimeStats.size - 1) / 100f)
                    }
                    for (index in 0 until watchTimeStats.size - 1) {
                        if (index != 0) {
                            colorsSaturation.add(mainColor.copy(((colorsSaturation[index - 1].alpha - colorStep.value))))
                        } else {
                            colorsSaturation.add(mainColor)
                        }
                    }

                    Column {
                        for (index in 1 until watchTimeStats.size) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.clickable {
                                    activePiePart = if (activePiePart != index - 1) {
                                        index - 1
                                    } else {
                                        -1
                                    }
                                }) {
                                Box(
                                    modifier = Modifier
                                        .size(12.dp)
                                        .background(colorsSaturation[index - 1])
                                )
                                Spacer(modifier = Modifier.width(tinySpacerValue))
                                Text(
                                    text = "${watchTimeStats[index].genre}: ${
                                        getFormattedTimeFromSeconds(
                                            watchTimeStats[index].totalWatchTimeInSeconds
                                        )
                                    }",
                                    color = MaterialTheme.colors.onSurface,
                                    fontSize = fontSize
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.width(mediumSpacerValue))
                    BoxWithConstraints(
                        modifier = Modifier
                            .fillMaxWidth(), contentAlignment = Alignment.Center
                    ) {
                        val size =
                            if (width < Constants.BIG_SCREEN_THRESHOLD) maxWidth else maxWidth / 2


                        PieChart(
                            modifier = Modifier.size(size = size),
                            progress = watchTimeStats.subList(1, watchTimeStats.size)
                                .map { it.totalWatchTimeInSeconds.toFloat() },
                            colors = colorsSaturation,
                            isDonut = true,
                            activePie = activePiePart,
                            onActivePiePieceSelected = {
                                activePiePart = it
                            },
                            fontSize = fontSize * 3
                        )
                    }
                }
            }
        }
    }
}

fun getFormattedTimeFromSeconds(seconds: Int): String {
    if (seconds < 60) return "< 1 мин"

    val hours = seconds / 3600
    val minutes = (seconds % 3600) / 60

    return when {
        hours == 0 -> {
            "$minutes мин"
        }
        hours < 24 -> {
            "$hours ч, $minutes мин"
        }
        else -> {
            val days = hours / 24
            val newHours = hours % 24
            val dayString =
                if (days < 2) "ден" else "дни"
            "$days $dayString, $newHours ч"
        }
    }

}