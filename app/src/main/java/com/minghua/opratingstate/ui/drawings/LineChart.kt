package com.minghua.opratingstate.ui.drawings

import android.content.ContentValues.TAG
import android.graphics.Path
import android.graphics.PointF
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.dp
import com.madrapps.plot.line.DataPoint
import com.minghua.opratingstate.utils.xCoordination
import com.minghua.opratingstate.utils.yCoordination
import kotlin.math.*

/**
 * @param times 横轴的时间
 * @param color 折线图，线的颜色
 * @param data 折线图的数据
 * @param chartTitle 折线图的标题
 */
//
@Composable
fun LineChart(
    times: List<String>,
    color: List<Color>,
    vararg data: List<DataPoint>,
    chartTitle: String = ""
) {
    if (times.isEmpty()) return
    Card(
        shape = RoundedCornerShape(10),
        elevation = 10.dp,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.618f)
            .padding(15.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(chartTitle)
        }
        Canvas(modifier = Modifier.fillMaxSize()) {
            val height = size.height - 16.dp.toPx()
            val width = size.width
            var maxValue = data.map { it.maxOf { d -> d.y } }.maxOf { f -> f }
            data.forEachIndexed first@{ index, it ->
                if (it.isEmpty()) return@first
                if (chartTitle.contains("效率")) {
                    maxValue = 0.2f
                    val efficiencyData = it.map { d -> DataPoint(d.x, d.y - 0.8f) }
                    val arrayList = it as ArrayList<DataPoint>
                    arrayList.clear()
                    arrayList.addAll(efficiencyData)
                }
                val path = Path()
                path.moveTo(
                    xCoordination(width, it[0].x, it.size),
                    yCoordination(height, it[0].y, maxValue)
                )
                var controlX = xCoordination(width, it[0].x, it.size)
                var controlY = yCoordination(height, it[0].y, maxValue)
                it.forEachIndexed second@{ index1, dataPoint ->
                    if (index1 == 0) return@second
                    val endX = (controlX + xCoordination(width, dataPoint.x, it.size)) / 2
                    val endY = (controlY + yCoordination(height, dataPoint.y, maxValue)) / 2
                    path.quadTo(controlX, controlY, endX, endY)
                    controlX = xCoordination(width, dataPoint.x, it.size)
                    controlY = yCoordination(height, dataPoint.y, maxValue)
                }
                path.lineTo(
                    xCoordination(width, it[it.size - 1].x, it.size),
                    yCoordination(height, it[it.size - 1].y, maxValue)
                )
                drawPath(
                    path.asComposePath(),
                    color = color[index],
                    style = Stroke(width = 4f, cap = StrokeCap.Round)
                )
                path.lineTo(width, height)
                path.lineTo(
                    xCoordination(width, it[0].x, it.size),
                    height
                )
                path.close()
                drawPath(
                    path.asComposePath(),
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            color[index].copy(alpha = color[index].alpha / 2),
                            Color.Transparent
                        )
                    )
                )
                //上面绘制的都是曲线，下面绘制横轴的时间
                //初始化画笔
                val paint = Paint()
                val frameworkPaint = paint.asFrameworkPaint()
                frameworkPaint.run {
                    isAntiAlias = true
                    textSize = 8.dp.toPx()
                    this.color = Color.Black.toArgb()
                }
                drawIntoCanvas {
                    //确定没一个x坐标的位置
                    val xLabelCount = if (times.size > 8) 8 else times.size
                    val indexes = (1 until xLabelCount).map { n -> times.size / xLabelCount * n }
                    val labels = times.filterIndexed { index, _ -> index in indexes }
                        .map { t -> t.substring(0 until 5) }
                    val labelPositions = (1 until xLabelCount).map { n ->
                        PointF(
                            size.width / xLabelCount * n,
                            size.height - 6.dp.toPx()
                        )
                    }
                    //将x轴信息绘制到底部
                    labelPositions.forEachIndexed { index, pointF ->
                        it.nativeCanvas.drawText(
                            labels[index],
                            pointF.x - frameworkPaint.measureText(labels[index]) / 2,
                            pointF.y,
                            frameworkPaint
                        )
                    }
                }
                //然后是纵轴信息
                val yLabels: List<Int>
                var power = floor(log(maxValue, 10f)).toInt()
                var factor = maxValue / 10.0.pow(power.toDouble())
                Log.d(TAG, "LineChart: $chartTitle : $factor")
                if (factor < 4) {
                    factor *= 10
                    power -= 1
                    yLabels = (1..7).map {
                        (factor / 8 * it).roundToInt()
                    }
                } else {
                    yLabels = (1..factor.toInt()).map {
                        (factor / (factor.toInt() + 1) * it).roundToInt()
                    }
                }
                
                val yPositions: List<Float> =
                    yLabels.map {
                        yCoordination(
                            height,
                            it * 10.0.pow(power.toDouble()).toFloat(),
                            maxValue
                        )
                    }
                yLabels.forEachIndexed { yLabelIndex, yLabel ->
                    drawLine(
                        brush = Brush.horizontalGradient(
                            listOf(
                                Color.LightGray,
                                Color.LightGray
                            )
                        ),
                        start = Offset(0f, yPositions[yLabelIndex]),
                        end = Offset(width, yPositions[yLabelIndex]),
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(5f, 5f))
                    )
                    drawIntoCanvas {
                        it.nativeCanvas.drawText(
                            if (power >= 0) (yLabel * 10.0.pow(power.toDouble())
                                .toInt()).toString() else String.format(
                                "%.${abs(power)}f",
                                (if (chartTitle.contains("效率")) yLabel + 80 else yLabel) * 10.0.pow(
                                    power.toDouble()
                                )
                            ),
                            0f,
                            yPositions[yLabelIndex],
                            frameworkPaint
                        )
                    }
                }
            }
        }
    }
}