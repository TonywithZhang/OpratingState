package com.minghua.opratingstate.utils

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.madrapps.plot.line.DataPoint
import com.minghua.opratingstate.R
import com.minghua.opratingstate.models.ProjectItemModel
import com.minghua.opratingstate.ui.theme.Teal200
import java.text.SimpleDateFormat
import java.util.*

val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).apply {
    timeZone = TimeZone.getTimeZone("Asia/Shanghai")
}

fun xCoordination(width: Float, x: Float, totalCount: Int): Float {
    return width * (x / (totalCount - 1))
}

fun yCoordination(height: Float, y: Float, maxValue: Float): Float {
    return height - y * (height / maxValue) * 0.95f
}

val colorGroup = listOf(Color.Red, Teal200)

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

//首页顶部的电站选择的项目

val  items = listOf(
    ProjectItemModel("创谷E栋屋顶", R.drawable.ic_device),
    ProjectItemModel("x屋顶", R.drawable.ic_device),
    ProjectItemModel("x屋顶", R.drawable.ic_device),
    ProjectItemModel("x屋顶", R.drawable.ic_device),
    ProjectItemModel("x屋顶", R.drawable.ic_device),
    ProjectItemModel("x屋顶", R.drawable.ic_device),
    ProjectItemModel("x屋顶", R.drawable.ic_device),
    ProjectItemModel("x屋顶", R.drawable.ic_device)
)

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

val timeSpec = listOf("日","月","年")