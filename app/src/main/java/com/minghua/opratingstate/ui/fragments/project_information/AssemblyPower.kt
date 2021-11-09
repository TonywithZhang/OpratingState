package com.minghua.opratingstate.ui.fragments.project_information

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.minghua.opratingstate.ui.drawings.LineChart
import com.minghua.opratingstate.ui.theme.topBarColor
import com.minghua.opratingstate.utils.colorGroup
import com.minghua.opratingstate.utils.lineChartData
import com.minghua.opratingstate.utils.times

@Composable
fun AssemblyPower(){
    Column(
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(5))
            .background(Color.White)
            .padding(5.dp)) {
        Row(Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.Center) {
            Text(
                text = "光伏组件功率",
                modifier = Modifier
                    .clip(RoundedCornerShape(15))
                    .background(topBarColor)
                    .padding(3.dp)
            )
        }
        LineChart(times = times, color = colorGroup, lineChartData, chartTitle = "直流输入电压对比",legends = listOf("PV1"))
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAssemblyPower(){
    Surface {
        AssemblyPower()
    }
}