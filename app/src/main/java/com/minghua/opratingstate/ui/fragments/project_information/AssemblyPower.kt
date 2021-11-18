package com.minghua.opratingstate.ui.fragments.project_information

import android.content.ContentValues.TAG
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.madrapps.plot.line.DataPoint
import com.minghua.opratingstate.network.repositories.localRoofRepo
import com.minghua.opratingstate.ui.drawings.LineChart
import com.minghua.opratingstate.ui.theme.topBarColor
import com.minghua.opratingstate.utils.colorGroup
import com.minghua.opratingstate.utils.lineChartData
import com.minghua.opratingstate.utils.times
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.apache.poi.ss.usermodel.charts.LineChartData
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
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
                text = "光伏发电功率",
                modifier = Modifier
                    .clip(RoundedCornerShape(15))
                    .background(topBarColor)
                    .padding(3.dp)
            )
        }
        var dataList by remember{ mutableStateOf(arrayOf(lineChartData)) }
        var chartTime: List<String> by remember{ mutableStateOf(times)}
        var maxRadiation by remember{ mutableStateOf(0L)}
        LaunchedEffect(key1 = 0)
        {
            withContext(Dispatchers.IO)
            {
                val state = localRoofRepo().getOutputRadiation()
                if (state.times.isNotEmpty())
                {
                    val maxPower = state.dataList[0].maxOf { d -> d.y }
                    Log.d(TAG, "AssemblyPower: $maxRadiation")
                    withContext(Dispatchers.Main){
                        chartTime = state.times.map { d -> d.split(" ")[1] }
                        dataList = arrayOf(ArrayList(state.dataList[0]),
                            ArrayList(state.dataList[1].map { d -> DataPoint(d.x,d.y * maxPower / maxRadiation) })
                        )
                        maxRadiation = state.dataList[1].maxOf { d -> d.y }.toLong()
                    }
                }
            }
        }
        LineChart(times = chartTime, color = colorGroup, *dataList, chartTitle = "",legends = listOf("输出功率","辐照强度"), rightAxisMax = maxRadiation)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PreviewAssemblyPower(){
    Surface {
        AssemblyPower()
    }
}