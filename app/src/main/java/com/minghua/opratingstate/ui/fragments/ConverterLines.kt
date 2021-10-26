package com.minghua.opratingstate.ui.fragments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.minghua.opratingstate.ui.drawings.LineChart
import com.minghua.opratingstate.utils.colorGroup
import com.minghua.opratingstate.utils.lineChartData
import com.minghua.opratingstate.utils.times

@Composable
fun ConverterLine(){
    Column(modifier = Modifier.fillMaxWidth()) {
        Box(modifier = Modifier.fillMaxWidth().height(35.dp).background(Color(0xffA8D08D))){
            Text(text = "逆变器效率",style = MaterialTheme.typography.h5, modifier = Modifier.align(
                Alignment.Center))
        }
        LineChart(times = times, color = colorGroup, lineChartData, chartTitle = "直流输入电压对比",legends = listOf("PV1"))

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewConverterLine(){
    ConverterLine()
}