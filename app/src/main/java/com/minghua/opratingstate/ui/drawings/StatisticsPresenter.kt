package com.minghua.opratingstate.ui.drawings

import android.graphics.Paint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.minghua.opratingstate.utils.timeSpec

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
                val height = size.height
                val width = size.width
                //初始化画笔
                val paint = android.graphics.Paint().apply {
                    textSize = 14.dp.toPx()
                }
                val segmentWidth = width / timeSpec.size
                //计算每个标题位置
                val textCoordination = timeSpec.mapIndexed { indexed, time ->
                    val textSize = paint.measureText(time)
                    indexed * segmentWidth + 0.5f * segmentWidth - 0.5f * textSize
                }
                val centerCoordination = timeSpec.mapIndexed { indexed, _ ->
                    indexed * segmentWidth + 5
                }
                drawRoundRect(
                    color = Color.White,
                    topLeft = Offset(rectPosition.value * segmentWidth + 5.dp.toPx(), 2.dp.toPx()),
                    size = Size(segmentWidth - 10.dp.toPx(), 16.dp.toPx()),
                    style = Fill
                )
                drawIntoCanvas {
                    it.drawRoundRect(rectPosition.value * segmentWidth + 5.dp.toPx(),
                        2.dp.toPx(),
                        rectPosition.value * segmentWidth - 5.dp.toPx() + segmentWidth,
                        18.dp.toPx(),
                        3.dp.toPx(),
                        3.dp.toPx(),
                        androidx.compose.ui.graphics.Paint().apply {
                            color = Color.White
                            asFrameworkPaint().run {
                                setShadowLayer(5f,1.dp.toPx(),1.dp.toPx(),0xFF888888)
                            }
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
        }

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewStatisticsPresenter() {
    Surface {
        StatisticsPresenter()
    }
}