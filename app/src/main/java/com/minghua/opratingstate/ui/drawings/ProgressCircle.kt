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
import androidx.compose.ui.tooling.preview.Preview
import kotlin.math.min

@Composable
fun ProgressCircle(progress: Float, modifier: Modifier = Modifier) {
    var initialed by remember{
        mutableStateOf(false)
    }
    val animateProgress = animateFloatAsState(
        targetValue = if (initialed) progress else 0f,
        animationSpec = tween(1500)
    )
    LaunchedEffect(key1 = 0){
        initialed = true
    }
    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height
        drawCircle(
            Color(0xff4672C4),
            radius = min(height, width) / 2,
            center = Offset(width / 2, height / 2),
            style = Stroke(width = 2f),
            alpha = 0.5f
        )
        drawArc(
            Color(0xff4672C4),
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
    }
}

@Preview(showBackground = true)
@Composable
fun ProgressCirclePreview() {
    ProgressCircle(0.3f, modifier = Modifier.fillMaxSize())
}