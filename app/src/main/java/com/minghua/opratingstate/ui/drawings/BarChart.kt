package com.minghua.opratingstate.ui.drawings

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.minghua.opratingstate.models.BarChartDataModel
import com.minghua.opratingstate.utils.barModels

@Composable
fun BarChart(chartData: List<BarChartDataModel>) {
    if (chartData.isEmpty()) return
    Card(
        Modifier
            .fillMaxWidth()
            .aspectRatio(1.618f)
            .padding(15.dp),
        shape = RoundedCornerShape(10),
        elevation = 10.dp
    ) {
        var heightFactor by remember { mutableStateOf(0f) }
        val animateHeight by animateFloatAsState(targetValue = heightFactor)
        LaunchedEffect(key1 = chartData, block = { heightFactor = 1f })
        Canvas(modifier = Modifier.fillMaxSize()) {
            val height = size.height
            val width = size.width
            val barWidth = width / chartData.size * 0.56f
            val maxValue = chartData.maxOf { c -> c.value }
            val barHeight = chartData.map {
                (height - 25.dp.toPx()) * it.value / maxValue * 0.85f
            }
            val barPositions = barHeight.mapIndexed { index, fl ->
                Offset(
                    width / chartData.size * (index + 0.1f) + 5.dp.toPx(),
                    (height - 25.dp.toPx()) - fl * animateHeight
                )
            }
            //h绘制标签的画笔
            val paint = android.graphics.Paint().apply {
                textSize = 14.dp.toPx()
            }
            barPositions.forEachIndexed { index, offset ->
                drawRect(
                    Brush.verticalGradient(listOf(Color(0xff59b9b8), Color(0xff7e7e7e))),
                    topLeft = offset,
                    size = Size(barWidth, barHeight[index])
                )
                drawIntoCanvas {
                    val textWidth = paint.measureText(chartData[index].time)
                    it.nativeCanvas.drawText(
                        chartData[index].time,
                        offset.x + barWidth / 2 - textWidth / 2,
                        height - 9.dp.toPx(),
                        paint
                    )
                    it.nativeCanvas.drawText(
                        String.format("%.1f",chartData[index].value),
                        offset.x + barWidth / 2 - textWidth / 2,
                        offset.y - 5,
                        paint
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBarChart() {
    Surface {
        BarChart(barModels)
    }
}