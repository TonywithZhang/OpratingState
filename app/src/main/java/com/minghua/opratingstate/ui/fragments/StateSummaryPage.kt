package com.minghua.opratingstate.ui.fragments

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.madrapps.plot.line.DataPoint
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@Composable
fun StateSummary() {
    val state = rememberScrollState()
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        //直流输入电压对比
        item {

        }

    }
}

@Composable
fun LineChart(times: List<String>, color: Color, vararg data: List<DataPoint>) {
    Card(
        shape = RoundedCornerShape(10),
        elevation = 10.dp,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.618f)
            .padding(15.dp)
    ) {
//        LineGraph(
//            plot = LinePlot(
//                lines = data.map { c ->
//                    LinePlot.Line(
//                        c,
//                        LinePlot.Connection(
//                            color,
//                            2.dp,
//                            StrokeCap.Round,
//                            pathEffect = PathEffect.cornerPathEffect(2f)
//                        ),
//                        intersection = LinePlot.Intersection(radius = 4.dp)
//                    )
//                },
//                xAxis = LinePlot.XAxis(steps = 15,content = {f1,f2,f3 ->
//                    for (index in 0 until 15){
//                        val currentValue = index * f2 + f1
//                        if (currentValue.toInt() > times.size) throw IllegalArgumentException("图表的点数量超过了横轴标签数量！")
//                        Text(text = times[currentValue.toInt()])
//                        if (currentValue > f3) {
//                            break
//                        }
//                    }
//                })
//            ), modifier = Modifier.fillMaxSize()
//        )
        Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.Center) {
            Text("直流输入电压对比")
        }
        Canvas(modifier = Modifier.fillMaxSize()) {
            val height = size.height
            val width = size.width
            data.forEach {
                val yMax = it.maxOf { d -> d.y }
                if (it.isEmpty()) return@forEach
                val path = android.graphics.Path()
                path.moveTo(xCoordination(width, it[0].x, it.size),
                    yCoordination(height, it[0].y, yMax))
                var controlX = xCoordination(width, it[0].x, it.size)
                var controlY = yCoordination(height, it[0].y, yMax)
                it.forEachIndexed { index, dataPoint ->
                    if (index == 0) return@forEachIndexed
//                    path.lineTo(
//                        xCoordination(width, dataPoint.x, it.size),
//                        yCoordination(height, dataPoint.y, yMax),
//                    )
                    val endX =  (controlX + xCoordination(width, dataPoint.x, it.size)) / 2
                    val endY = (controlY + yCoordination(height, dataPoint.y, yMax)) / 2
                    path.quadTo(controlX, controlY, endX, endY)
                    controlX = xCoordination(width, dataPoint.x, it.size)
                    controlY = yCoordination(height, dataPoint.y, yMax)
                }
                drawPath(path.asComposePath(),color = color,style = Stroke(width = 4f,cap = StrokeCap.Round))
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
                            color.copy(alpha = color.alpha / 2),
                            Color.Transparent
                        )
                    )
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewStateSummary() {
    val times = ArrayList<String>().apply {
        val baseTime = GregorianCalendar.getInstance()
        val dateFormat = SimpleDateFormat("HH:mm:ss", Locale.CHINA).apply {
            timeZone = TimeZone.getTimeZone("Asia/Shanghai")
        }
        for (index in 0 until 100) {
            baseTime.add(Calendar.MINUTE, 1)
            add(dateFormat.format(baseTime.time))
        }
    }
    val chartData = ArrayList<DataPoint>().apply {
        for (index in 0 until 100) {
            val r = Random(System.currentTimeMillis())
            add(DataPoint(index.toFloat(), (5 * index - r.nextInt(10)).toFloat()))
        }
    }
    Surface(modifier = Modifier.fillMaxSize()) {
        //StateSummary()
        LineChart(times = times, color = Color.Red, chartData)
    }
}

fun xCoordination(width: Float, x: Float, totalCount: Int): Float {
    return x * (width * 1.0 / totalCount).toFloat()
}

fun yCoordination(height: Float, y: Float, maxValue: Float): Float {
    return height - y * (height / maxValue) * 0.95f
}