package com.minghua.opratingstate.ui.fragments

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.madrapps.plot.line.DataPoint
import com.minghua.opratingstate.R
import com.minghua.opratingstate.models.LineData
import com.minghua.opratingstate.models.LocalRoofStateModel
import com.minghua.opratingstate.network.repositories.localRoofRepo
import com.minghua.opratingstate.ui.drawings.DetailProjectItem
import com.minghua.opratingstate.ui.drawings.LineChart
import com.minghua.opratingstate.ui.drawings.ProgressCircle
import com.minghua.opratingstate.ui.drawings.StatisticsPresenter
import com.minghua.opratingstate.ui.theme.topBarColor
import com.minghua.opratingstate.utils.lineChartData
import com.minghua.opratingstate.utils.colorGroup
import com.minghua.opratingstate.utils.items
import com.minghua.opratingstate.utils.times
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.listItems
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalAnimationApi
@Composable
fun LandScape(userName: String = "", navController: NavHostController?) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(topBarColor),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "资产概览", style = MaterialTheme.typography.h5)
        }
        val dialogState = rememberMaterialDialogState()
        MaterialDialog(dialogState = dialogState) {
            listItems(
                list = items,
                onClick = { _, item -> navController?.navigate("detailItem/${item.name}") }) { _, item ->
                DetailProjectItem(model = item)
            }
        }
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            mainAxisAlignment = FlowMainAxisAlignment.SpaceAround
        ) {
            items.forEachIndexed loop@{ index, itemModel ->
                if (index >= 7) return@loop
                ProjectItem(
                    iconId = itemModel.iconId,
                    name = itemModel.name,
                    modifier = Modifier
                        .fillMaxWidth(0.22f)
                        .clickable { navController?.navigate("detailItem/${itemModel.name}") }
                )
            }
            if (items.size >= 8)
                ProjectItem(
                    iconId = R.drawable.ic_more,
                    name = "更多",
                    modifier = Modifier
                        .fillMaxWidth(0.22f)
                        .clickable { dialogState.show() }
                )
//                Button(
//                    onClick = { dialogState.show() },
//                    colors = buttonColors(backgroundColor = Color.Transparent),
//                    elevation = elevation(0.dp)
//                ) {
//                    ProjectItem(
//                        iconId = R.drawable.ic_more,
//                        name = "更多",
//                        modifier = Modifier.fillMaxWidth(0.22f)
//                    )
//                }
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
        var showLineChart by remember {
            mutableStateOf(false)
        }
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
                        topBarColor,
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
        var currentData by remember {
            mutableStateOf(
                LocalRoofStateModel(
                    listOf(
                        LineData(
                            lineChartData.map { c -> c.y.toDouble() },
                            "功率",
                            "line"
                        )
                    ), times, ""
                )
            )
        }
        var currentPower by remember { mutableStateOf(0) }
        Row(
            Modifier
                .fillMaxWidth()
                .padding(top = 10.dp), horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "实时功率：$currentPower W")
        }


        LaunchedEffect(key1 = refreshCount) {
            try {
                withContext(Dispatchers.IO) {
                    val result = localRoofRepo().localRoofPower()
                    withContext(Dispatchers.Main) {
                        currentData = result
                        loadingData = false
                        showLineChart = true
                        if (result.lineData.all { l -> l.data.isNotEmpty() })
                            currentPower =
                                result.lineData.sumOf { l -> l.data[l.data.size - 1] }.toInt()
                    }
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
        AnimatedVisibility(visible = showLineChart) {
            LineChart(
                times = currentData.times,
                color = colorGroup,
                data = currentData.lineData.map { l ->
                    l.data.mapIndexed { index, d ->
                        DataPoint(
                            index.toFloat(),
                            d.toFloat()
                        )
                    }
                }.toTypedArray(),
                legends = listOf("PV1功率", "PV2功率")
            )
//            LineChart(times = times, color = colorGroup, lineChartData, chartTitle = "直流输入电压对比",legends = listOf("PV1"))
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
                        topBarColor,
                        RoundedCornerShape(20)
                    )
                    .padding(horizontal = 15.dp, vertical = 5.dp)
                    .wrapContentHeight()
            )
        }
        StatisticsPresenter()
        Spacer(modifier = Modifier.height(100.dp))
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalAnimationApi
@Preview(showBackground = true)
@Composable
fun LandScapePreview() {
    LandScape(userName = "长阳创谷E栋屋顶", null)
}