package eu.bbsapps.forgottenfilmsapp.presentation.components.stats

import android.graphics.Paint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.TextUnit
import kotlin.math.atan2
import kotlin.math.roundToInt

@Composable
fun PieChart(
    modifier: Modifier = Modifier,
    progress: List<Float>,
    colors: List<Color>,
    isDonut: Boolean = false,
    percentColor: Color = MaterialTheme.colors.onSurface,
    activePie: Int,
    onActivePiePieceSelected: (Int) -> Unit,
    fontSize: TextUnit
) {
    val total = progress.sum()
    val proportions = progress.map {
        it * 100 / total
    }

    val angleProgress = proportions.map {
        360 * it / 100
    }

    val progressSize = mutableListOf<Float>()
    progressSize.add(angleProgress.first())

    for (x in 1 until angleProgress.size)
        progressSize.add(angleProgress[x] + progressSize[x - 1])

    var startAngle = 270f

    BoxWithConstraints(modifier = modifier, contentAlignment = Alignment.Center) {
        val sideSize = constraints.maxWidth / 1.1f
        val padding = (sideSize * if (isDonut) 30 else 20) / 100f
        val pathPortion = remember {
            Animatable(initialValue = 0f)
        }
        LaunchedEffect(key1 = true) {
            pathPortion.animateTo(
                1f, animationSpec = tween(1000)
            )
        }

        val size = Size(sideSize - padding, sideSize - padding)
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(true) {

                    if (!isDonut)
                        return@pointerInput

                    detectTapGestures {
                        val clickedAngle = convertTouchEventPointToAngle(
                            sideSize,
                            sideSize,
                            it.x,
                            it.y
                        )
                        progressSize.forEachIndexed { index, item ->
                            if (clickedAngle <= item) {
                                if (activePie != index) {
                                    onActivePiePieceSelected(index)
                                }
                                return@detectTapGestures
                            }
                        }
                    }
                }
        ) {

            angleProgress.forEachIndexed { index, arcProgress ->
                drawPie(
                    colors[index],
                    startAngle,
                    arcProgress * pathPortion.value,
                    size,
                    padding = padding,
                    isDonut = isDonut,
                    isActive = activePie == index
                )
                startAngle += arcProgress
            }

            if (activePie != -1)
                drawContext.canvas.nativeCanvas.apply {
                    drawText(
                        "${proportions[activePie].roundToInt()}%",
                        (sideSize / 2) + fontSize.value / 4, (sideSize / 2) + fontSize.value / 3,
                        Paint().apply {
                            color = percentColor.toArgb()
                            textSize = fontSize.value
                            textAlign = Paint.Align.CENTER
                        }
                    )
                }
        }
    }
}

private fun DrawScope.drawPie(
    color: Color,
    startAngle: Float,
    arcProgress: Float,
    size: Size,
    padding: Float,
    isDonut: Boolean = false,
    isActive: Boolean = false
): Path {

    return Path().apply {
        drawArc(
            color = color,
            startAngle = startAngle,
            sweepAngle = arcProgress,
            useCenter = !isDonut,
            size = size,
            style = if (isDonut) Stroke(
                width = if (isActive) 80f else 60f,
            ) else Fill,

            topLeft = Offset(padding / 2, padding / 2)
        )
    }
}

private fun convertTouchEventPointToAngle(
    width: Float,
    height: Float,
    xPos: Float,
    yPos: Float
): Double {
    val x = xPos - (width * 0.5f)
    val y = yPos - (height * 0.5f)
    var angle = Math.toDegrees(atan2(y.toDouble(), x.toDouble()) + Math.PI / 2)
    angle = if (angle < 0) angle + 360 else angle
    return angle
}
