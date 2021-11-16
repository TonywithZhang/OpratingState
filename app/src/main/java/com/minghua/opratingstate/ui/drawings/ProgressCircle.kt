package com.minghua.opratingstate.ui.drawings

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.min
import kotlin.math.roundToInt

@Composable
fun ProgressCircle(
    progress: Float,
    modifier: Modifier = Modifier,
    showText: Boolean = false,
    textToInt: Boolean = false,
    color: Color = Color(0xff4672C4)
) {
    var initialed by remember {
        mutableStateOf(false)
    }
    val animateProgress = animateFloatAsState(
        targetValue = if (initialed) progress else 0f,
        animationSpec = tween(1500)
    )
    LaunchedEffect(key1 = 0) {
        initialed = true
    }
    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height
        drawCircle(
            color,
            radius = min(height, width) / 2,
            center = Offset(width / 2, height / 2),
            style = Stroke(width = 2f),
            alpha = 0.5f
        )
        drawArc(
            color,
            -90f,
            animateProgress.value * 360,
            false,
            style = Stroke(width = 8f),
            topLeft = Offset(
                if (height > width) 0f else maxOf(height, width) / 2 - minOf(height, width) / 2,
                if (height > width) maxOf(height, width) / 2 - minOf(height, width) / 2 else 0f
            ),
            size = Size(minOf(height, width), minOf(height, width))
        )
        if (showText) {
            val paint = android.graphics.Paint().apply {
                textSize = 14.dp.toPx()
                this.color = color.toArgb()
            }
            val progressText = if (!textToInt)String.format("%.2f%%", progress * 100)else (progress*100).roundToInt().toString() + "%"
            val textLength = paint.measureText(progressText)
            drawIntoCanvas {
                it.nativeCanvas.drawText(
                    progressText,
                    width / 2 - textLength / 2,
                    height / 2 + 7.dp.toPx(),
                    paint
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProgressCirclePreview() {
    ProgressCircle(0.3f, modifier = Modifier.fillMaxSize(), true)
}