package com.minghua.opratingstate.ui.drawings

import android.content.ContentValues.TAG
import android.graphics.Paint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.minghua.opratingstate.models.BarChartDataModel
import com.minghua.opratingstate.utils.barModels
import com.minghua.opratingstate.utils.timeSpec
import com.minghua.opratingstate.utils.times
import java.lang.IllegalArgumentException
import java.time.LocalDate
import java.time.Month
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjuster
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StatisticsPresenter() {
    var index by remember {
        mutableStateOf(0)
    }
    val rectPosition = animateFloatAsState(targetValue = index.toFloat())
    Column(
        Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .background(Color.LightGray, RoundedCornerShape(15))
        ) {
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
            ) {
                val width = size.width
                //初始化画笔
                val segmentWidth = width / timeSpec.size
//                drawRoundRect(
//                    color = Color.White,
//                    topLeft = Offset(rectPosition.value * segmentWidth + 5.dp.toPx(), 2.dp.toPx()),
//                    size = Size(segmentWidth - 10.dp.toPx(), 16.dp.toPx()),
//                    style = Fill
//                )
                drawIntoCanvas {
                    it.drawRoundRect(rectPosition.value * segmentWidth + 5.dp.toPx(),
                        2.dp.toPx(),
                        rectPosition.value * segmentWidth - 5.dp.toPx() + segmentWidth,
                        18.dp.toPx(),
                        3.dp.toPx(),
                        3.dp.toPx(),
                        androidx.compose.ui.graphics.Paint().apply {
                            color = Color.White
                            asFrameworkPaint().setShadowLayer(
                                5f,
                                2.dp.toPx(),
                                2.dp.toPx(),
                                0x888888
                            )
                        })
//                    timeSpec.forEachIndexed { indexed, time ->
//                        it.nativeCanvas.drawText(
//                            time,
//                            textCoordination[indexed],
//                            16.dp.toPx(),
//                            paint
//                        )
//                    }
                }
            }

            Row(
                Modifier
                    .fillMaxWidth()
                    .height(20.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                timeSpec.forEachIndexed { indexed, time ->
                    Text(
                        text = time,
                        Modifier
                            .wrapContentWidth()
                            .clickable { index = indexed }, textAlign = TextAlign.Center
                    )
                }
            }
        }
        //柱状图的数据
        var barData by remember { mutableStateOf(barModels) }
        //时间选择器上显示的文字
        var today by remember{ mutableStateOf(LocalDate.now())}
//        val calendar = Calendar.getInstance()
//        val startDay = calendar.add(Calendar.DAY_OF_MONTH,-7)
        var startDay: LocalDate
        var timeSpan by remember {
            mutableStateOf(
                ""
            )
        }
        LaunchedEffect(key1 = index, key2 = today) {
            Log.d(TAG, "StatisticsPresenter: 更新timespan")
            timeSpan = when(index){
                0 -> {
                    startDay = today.plusDays(-7)
                    "${startDay.format(DateTimeFormatter.ISO_DATE)} ~ ${
                        today.format(
                            DateTimeFormatter.ISO_DATE
                        )
                    }"
                }
                1 -> {
                    startDay = today.plusMonths(-6)
                    "${startDay.year}-${startDay.monthValue}~ ${today.year}-${today.monthValue}"
                }
                2 -> {
                    startDay = today.plusYears(-2)
                    "${startDay.year}~ ${today.year}"
                }
                else -> {throw IllegalArgumentException("日期选择器参数错误！！")}
            }

        }
        TimeSpanSelector(timeSpan = timeSpan, forward = {
            when(index){
                0 -> {
                    startDay = today
                    today = today.plusDays(7)
                }
                1 -> {
                    startDay = today
                    today = today.plusMonths(6)
                }
                2 -> {
                    startDay = today
                    today = today.plusYears(2)
                }
                else -> {throw IllegalArgumentException("日期选择器参数错误！！")}
            }
        }) {
            when(index){
                0 -> {
                    Log.d(TAG, "StatisticsPresenter: 日期减")
                    startDay = today.plusDays(-14)
                    today = today.plusDays(-7)
                }
                1 -> {
                    startDay = today.plusMonths(-12)
                    today = today.plusMonths(-6)
                }
                2 -> {
                    startDay = today.plusYears(-4)
                    today = today.plusYears(-2)
                }
                else -> {throw IllegalArgumentException("日期选择器参数错误！！")}
            }
        }
        BarChart(chartData = barData)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PreviewStatisticsPresenter() {
    Surface {
        StatisticsPresenter()
    }
}