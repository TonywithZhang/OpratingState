package com.minghua.opratingstate.ui.fragments

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.ButtonDefaults.elevation
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.madrapps.plot.line.DataPoint
import com.minghua.opratingstate.R
import com.minghua.opratingstate.models.LineData
import com.minghua.opratingstate.models.LocalRoofStateModel
import com.minghua.opratingstate.models.LocalRoofSum
import com.minghua.opratingstate.network.repositories.localRoofRepo
import com.minghua.opratingstate.ui.drawings.LineChart
import com.minghua.opratingstate.ui.drawings.ProgressCircle
import com.minghua.opratingstate.ui.drawings.StatisticsPresenter
import com.minghua.opratingstate.utils.chartData
import com.minghua.opratingstate.utils.colorGroup
import com.minghua.opratingstate.utils.items
import com.minghua.opratingstate.utils.times
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.lang.Exception

@ExperimentalAnimationApi
@Composable
fun LandScape(userName: String = "") {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .scrollable(scrollState, orientation = Orientation.Vertical)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(Color(0xffA8D08D)),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "资产概览", style = MaterialTheme.typography.h5)
        }
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            mainAxisAlignment = FlowMainAxisAlignment.SpaceBetween
        ) {
            items.forEachIndexed loop@{ index, itemModel ->
                if (index >= 7) return@loop
                ProjectItem(
                    iconId = itemModel.iconId,
                    name = itemModel.name,
                    modifier = Modifier.fillMaxWidth(0.22f)
                )
            }
            if (items.size >= 8)
                Button(
                    onClick = {},
                    colors = buttonColors(backgroundColor = Color.Transparent),
                    elevation = elevation(0.dp)
                ) {
                    ProjectItem(
                        iconId = R.drawable.ic_more,
                        name = "更多",
                        modifier = Modifier.fillMaxWidth(0.22f)
                    )
                }
        }
        Text(text = "实时统计", style = MaterialTheme.typography.h5)
        Row(
            Modifier
                .padding(top = 10.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProgressCircle(
                progress = 0.5f,
                Modifier
                    .height(30.dp)
                    .width(30.dp)
            )
            Column(Modifier.wrapContentSize(), horizontalAlignment = Alignment.Start) {
                Text(text = "年发电量/年计划发电量（万kWh）")
                Text(text = "6.45/200")
            }
        }
        var refreshCount by remember { mutableStateOf(0) }
        var loadingData by remember { mutableStateOf(false) }
        val rotateAnimation = rememberInfiniteTransition()
        val rotate = rotateAnimation.animateFloat(
            initialValue = 0f,
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                tween(durationMillis = 1000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            )
        )
        Row(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 30.dp), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "", Modifier.width(30.dp))
            Text(
                text = "发电功率",
                Modifier
                    .background(
                        Color(0xffA8D08D),
                        RoundedCornerShape(20)
                    )
                    .padding(horizontal = 15.dp, vertical = 5.dp)
                    .wrapContentHeight()
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_refresh),
                contentDescription = null,
                Modifier
                    .height(30.dp)
                    .clickable { refreshCount++; loadingData = true }
                    .rotate(if (loadingData) rotate.value else 0f)
            )
        }
        Row(
            Modifier
                .fillMaxWidth()
                .padding(top = 10.dp), horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "实时功率：3400W")
        }
        var currentData by remember {
            mutableStateOf(
                LocalRoofStateModel(
                    listOf(
                        LineData(
                            chartData.map { c -> c.y.toDouble() },
                            "功率",
                            "line"
                        )
                    ), times, ""
                )
            )
        }

        LaunchedEffect(key1 = refreshCount) {
            try {
                withContext(Dispatchers.IO) {
                    val result = localRoofRepo().localRoofPower()
                    withContext(Dispatchers.Main) {
                        currentData = result
                    }
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
        AnimatedVisibility(visible = refreshCount != 0) {
            LineChart(
                times = currentData.times,
                color = colorGroup,
                data = currentData.lineData.map { l ->
                    l.data.mapIndexed { d, index ->
                        DataPoint(
                            index.toFloat(),
                            d.toFloat()
                        )
                    }
                }.toTypedArray(),
                legends = listOf("发电功率","预测功率")
            )
        }
        Row(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 30.dp), horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "电量",
                Modifier
                    .background(
                        Color(0xffA8D08D),
                        RoundedCornerShape(20)
                    )
                    .padding(horizontal = 15.dp, vertical = 5.dp)
                    .wrapContentHeight()
            )
        }
        StatisticsPresenter()
    }
}

@ExperimentalAnimationApi
@Preview(showBackground = true)
@Composable
fun LandScapePreview() {
    LandScape()
}