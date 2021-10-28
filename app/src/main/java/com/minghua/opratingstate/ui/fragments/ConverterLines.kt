package com.minghua.opratingstate.ui.fragments

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.minghua.opratingstate.ui.drawings.LineChart
import com.minghua.opratingstate.ui.fragments.project_information.ConverterStatus
import com.minghua.opratingstate.ui.theme.stateColors
import com.minghua.opratingstate.ui.theme.topBarColor
import com.minghua.opratingstate.utils.colorGroup
import com.minghua.opratingstate.utils.converterSamples
import com.minghua.opratingstate.utils.lineChartData
import com.minghua.opratingstate.utils.times

@Composable
fun ConverterLine(name : String){
    LazyColumn(modifier = Modifier
        .fillMaxSize()) {
        item {
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(35.dp)
                .background(topBarColor)){
                Text(text = "逆变器效率",style = MaterialTheme.typography.h6, modifier = Modifier.align(
                    Alignment.Center))
            }
        }
        item {
            LineChart(times = times, color = colorGroup, lineChartData, chartTitle = "直流输入电压对比",legends = listOf("PV1"))
        }
        item {
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Canvas(modifier = Modifier.size(20.dp)){
                    drawRoundRect(stateColors[0], Offset.Zero, Size(20.dp.toPx(),20.dp.toPx()), CornerRadius(5.dp.toPx(),5.dp.toPx()))
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "25%功率点")
                    Text(text = "有效功率")
                }

                Canvas(modifier = Modifier.size(20.dp)){
                    drawRoundRect(stateColors[1], Offset.Zero, Size(20.dp.toPx(),20.dp.toPx()), CornerRadius(5.dp.toPx(),5.dp.toPx()))
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "50%功率点")
                    Text(text = "有效功率")
                }
                Canvas(modifier = Modifier.size(20.dp)){
                    drawRoundRect(stateColors[2], Offset.Zero, Size(20.dp.toPx(),20.dp.toPx()), CornerRadius(5.dp.toPx(),5.dp.toPx()))
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "100%功率点")
                    Text(text = "有效功率")
                }
            }
        }
        items(converterSamples){
            ConverterStatus(
                converterName = it.name,
                sampleNumber = it.sampleNumber,
                efficiency25 = it.efficiency25,
                efficiency50 = it.efficiency50,
                efficiency100 = it.efficiency100,
                progress = it.efficiency
            )
        }
        item {
            Spacer(modifier = Modifier.height(130.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewConverterLine(){
    ConverterLine("")
}