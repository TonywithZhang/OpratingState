package com.minghua.opratingstate.ui.fragments

import android.content.ContentValues.TAG
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.materialIcon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.madrapps.plot.line.DataPoint
import com.minghua.opratingstate.R
import com.minghua.opratingstate.models.LocalRoofStateModel
import com.minghua.opratingstate.network.repositories.localRoofRepo
import com.minghua.opratingstate.ui.drawings.LineChart
import com.minghua.opratingstate.utils.colorGroup
import com.minghua.opratingstate.utils.dateFormatter
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StateSummary() {
    var listData by remember {
        mutableStateOf(ArrayList<LocalRoofStateModel>())
    }
    val currentDate = remember { mutableStateOf(dateFormatter.format(Date())) }
    var p1 by remember {
        mutableStateOf("")
    }
    var p2 by remember {
        mutableStateOf("")
    }
    LaunchedEffect(key1 = currentDate.value) {
        val result = ArrayList<LocalRoofStateModel>()
        val produce1: Long
        val produce2: Long
        withContext(Dispatchers.IO) {
            val service = localRoofRepo()
            val ret = service.localRoofStates(time = currentDate.value)
            result.addAll(ret.state)
            produce1 = ret.p1
            produce2 = ret.p2
        }
        withContext(Dispatchers.Main) {
            listData = result
            p1 = "$produce1 kWh"
            p2 = "$produce2 kWh"
        }
    }
    val dialog = remember { MaterialDialog() }
    dialog.build(buttons = {
        positiveButton("Ok")
        negativeButton("Cancel")
    }) {
        datepicker { date ->
            currentDate.value = date.format(DateTimeFormatter.ISO_DATE)
        }
    }
    Column(modifier = Modifier.fillMaxWidth()) {
        Card(
            shape = RoundedCornerShape(15),
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .border(1.dp, Color.Gray, shape = RoundedCornerShape(15)),
            elevation = 5.dp
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    Row(modifier = Modifier.fillMaxWidth(),verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.pv1_icon),
                            contentDescription = null,
                            modifier = Modifier
                                .height(25.dp)
                                .width(25.dp)
                        )
                        Text(text = "PV1累计发电量：$p1")
                    }
                    Row(modifier = Modifier.fillMaxWidth(),verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.pv2_icon),
                            contentDescription = null,
                            modifier = Modifier
                                .height(25.dp)
                                .width(25.dp)
                        )
                        Text(text = "PV2累计发电量：$p2")
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .fillMaxHeight()
                        .clickable { dialog.show() },
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.calendar),
                        contentDescription = null,
                        modifier = Modifier
                            .height(25.dp)
                            .width(25.dp)
                    )
                    Text(text = "当前日期：")
                    Text(currentDate.value)
                }
            }
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            //直流输入电压对比
            items(listData) {
                LineChart(
                    times = it.times,
                    color = colorGroup,
                    data = it.lineData.map { l ->
                        val points = l.data.mapIndexed { index, d ->
                            DataPoint(
                                index.toFloat(),
                                d.toFloat()
                            )
                        }
                        ArrayList<DataPoint>().apply {
                            addAll(points)
                        }
                    }.toTypedArray(),
                    legends = it.lineData.map { l -> l.name },
                    chartTitle = it.title
                )
            }
        }
    }
}


@Preview
@Composable
fun PreviewStateSummary() {
    val times = ArrayList<String>().apply {
        val baseTime = GregorianCalendar.getInstance()
        val dateFormat = SimpleDateFormat("HH:mm:ss", Locale.CHINA).apply {
            timeZone = TimeZone.getTimeZone("Asia/Shanghai")
        }
        for (index in 0 until 100) {
            baseTime.add(Calendar.MINUTE, 1)
            add(dateFormat.format(baseTime.time))
        }
    }
    val chartData = ArrayList<DataPoint>().apply {
        for (index in 0 until 100) {
            val r = Random(System.currentTimeMillis())
            add(DataPoint(index.toFloat(), (5 * index - r.nextInt(10)).toFloat()))
        }
    }
    Surface(modifier = Modifier.fillMaxSize()) {
        //StateSummary()
        LineChart(times = times, color = listOf(Color.Red), chartData, chartTitle = "直流输入电压对比",legends = listOf("PV1"))
    }
}
