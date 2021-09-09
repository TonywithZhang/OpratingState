package com.minghua.opratingstate.ui.fragments

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.madrapps.plot.line.DataPoint
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
import java.util.*
import kotlin.collections.ArrayList

@Composable
fun StateSummary() {
    val scope = rememberCoroutineScope()
    var listData by remember {
        mutableStateOf(ArrayList<LocalRoofStateModel>())
    }
    val dialog = remember { MaterialDialog() }
    dialog.build(buttons = {
        positiveButton("Ok")
        negativeButton("Cancel")
    }) {
        datepicker { date ->

        }
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        scope.launch {
            val result = ArrayList<LocalRoofStateModel>()
            withContext(Dispatchers.IO) {
                val service = localRoofRepo()
                result.addAll(service.localRoofStates(time = dateFormatter.format(Date())))
            }
            withContext(Dispatchers.Main) {
                listData = result
            }
        }
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
                chartTitle = it.title
            )
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
        LineChart(times = times, color = listOf(Color.Red), chartData, chartTitle = "直流输入电压对比")
    }
}
