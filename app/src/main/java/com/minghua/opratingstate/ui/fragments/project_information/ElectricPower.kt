package com.minghua.opratingstate.ui.fragments.project_information

import android.content.ContentValues
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.ButtonDefaults.elevation
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.madrapps.plot.line.DataPoint
import com.minghua.opratingstate.R
import com.minghua.opratingstate.network.repositories.localRoofRepo
import com.minghua.opratingstate.ui.drawings.LineChart
import com.minghua.opratingstate.ui.theme.topBarColor
import com.minghua.opratingstate.utils.colorGroup
import com.minghua.opratingstate.utils.lineChartData
import com.minghua.opratingstate.utils.times
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun ElectricPower(controller: NavHostController?,name: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(5))
            .background(Color.White)
            .padding(3.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { },
                colors = buttonColors(
                    backgroundColor = Color.Transparent,
                    contentColor = Color.Transparent,
                    disabledBackgroundColor = Color.Transparent,
                    disabledContentColor = Color.Transparent
                ),
                enabled = false,
                modifier = Modifier.size(50.dp),
                elevation = elevation(0.dp)
            ) {

            }
            Text(
                text = "发电功率|电量",
                modifier = Modifier
                    .clip(RoundedCornerShape(15))
                    .background(topBarColor)
                    .padding(2.dp)
            )
            Button(
                onClick = { controller?.navigate("production/长阳创谷E栋屋顶") },
                colors = buttonColors(backgroundColor = Color.Transparent),
                elevation = elevation(0.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_more),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
        var dataList by remember{ mutableStateOf(lineChartData) }
        var chartTime: List<String> by remember{ mutableStateOf(times) }
        LaunchedEffect(key1 = 0)
        {
            withContext(Dispatchers.IO)
            {
                val state = localRoofRepo().getOutputRadiation()
                if (state.times.isNotEmpty())
                {
                    withContext(Dispatchers.Main){
                        chartTime = state.times.map { d -> d.split(" ")[1] }
                        dataList = ArrayList(state.dataList[0])
                    }
                }
            }
        }
        LineChart(times = chartTime, color = colorGroup, dataList,legends = listOf("发电功率"))
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewElectricPower() {
    Surface {
        ElectricPower(null,"")
    }
}