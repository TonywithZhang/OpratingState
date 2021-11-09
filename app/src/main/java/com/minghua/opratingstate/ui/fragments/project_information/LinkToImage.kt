package com.minghua.opratingstate.ui.fragments.project_information

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.minghua.opratingstate.ui.theme.topBarColor

@Composable
fun LinkToImage() {
    val infinity = rememberInfiniteTransition()
    val indicator by infinity.animateFloat(
        initialValue = 0f,
        targetValue = 2.99f,
        animationSpec = infiniteRepeatable(tween(1500),RepeatMode.Restart)
    )
    Canvas(modifier = Modifier
        .width(80.dp)
        .height(30.dp)) {
        val points = listOf(
            Offset(8.33f * 1.dp.toPx(), 5.dp.toPx()),
            Offset(18.33f * 1.dp.toPx(), 15.dp.toPx()),
            Offset(8.33f * 1.dp.toPx(), 25.dp.toPx())
        )
        val path = Path()
        path.moveTo(points[0].x,points[0].y)
        path.lineTo(points[1].x,points[1].y)
        path.lineTo(points[2].x,points[2].y)
        path.close()
        drawPath(path = path,color = if(indicator.toInt() % 3 == 0)Color.Yellow else topBarColor)
        path.translate(Offset(26.66f * 1.dp.toPx(),0f))
        drawPath(path = path,color = if(indicator.toInt() % 3 == 1)Color.Yellow else topBarColor)
        path.translate(Offset(26.66f * 1.dp.toPx(),0f))
        drawPath(path = path,color = if(indicator.toInt() % 3 == 2)Color.Yellow else topBarColor)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLinkImage(){
    androidx.compose.material.Surface {
        LinkToImage()
    }
}