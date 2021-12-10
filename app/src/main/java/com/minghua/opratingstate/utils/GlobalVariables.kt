package com.minghua.opratingstate.utils

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.madrapps.plot.line.DataPoint
import com.minghua.opratingstate.R
import com.minghua.opratingstate.models.*
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

//val colorGroup = listOf(Color(0xff00ffcc),Color.Red, Teal200)
val colorGroup = listOf(
    Color(0xffdedede),
    Color(0xff7e7e7e),
    Color(0xff59b9b8)
).reversed()

//val localServerIP = "10.151.112.201"
//val remoteServerIP = "101.132.236.192"
val serverIP = "10.151.112.201"

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

//首页顶部的电站选择的项目

val  items = listOf(
    ProjectItemModel("创谷E栋屋顶", R.drawable.ic_device,12,1),
    ProjectItemModel("x屋顶", R.drawable.ic_device,12,1),
    ProjectItemModel("x屋顶", R.drawable.ic_device,12,1),
    ProjectItemModel("x屋顶", R.drawable.ic_device,12,1),
    ProjectItemModel("x屋顶", R.drawable.ic_device,12,1),
    ProjectItemModel("x屋顶", R.drawable.ic_device,12,1),
    ProjectItemModel("x屋顶", R.drawable.ic_device,12,1),
    ProjectItemModel("x屋顶", R.drawable.ic_device,12,1)
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
val lineChartData = ArrayList<DataPoint>().apply {
    for (index in 0 until 100) {
        val r = Random(System.currentTimeMillis())
        add(DataPoint(index.toFloat(), (5 * index - r.nextInt(10)).toFloat()))
    }
}

val timeSpec = listOf("日","月","年")

val barModels = listOf(
    BarChartDataModel("9.5", 9.5f),
    BarChartDataModel("9.6", 9.6f),
    BarChartDataModel("9.7", 9.7f),
    BarChartDataModel("9.8", 9.8f),
    BarChartDataModel("9.9", 9.9f),
    BarChartDataModel("9.10", 9.1f),
    BarChartDataModel("9.11", 9.2f)
)

val converterSamples = listOf(
    ConverterSampleModel("1区A逆变器",2668,0.9790f,0.9778f,0.9845f,0.9828f),
    ConverterSampleModel("1区B逆变器",2857,0.9841f,0.9813f,0.9829f,0.9864f),
    ConverterSampleModel("2区A逆变器",7003,0.9790f,0.9778f,0.9845f,0.9828f),
    ConverterSampleModel("2区B逆变器",5758,0.9790f,0.9778f,0.9845f,0.9828f),
    ConverterSampleModel("3区A逆变器",7447,0.9790f,0.9778f,0.9845f,0.9828f),
    ConverterSampleModel("3区B逆变器",7769,0.9790f,0.9778f,0.9845f,0.9828f)
)

val lossSamples = listOf(
    LossSampleModel("光伏组串1",5,0.0274f,"2021-11-05"),
    LossSampleModel("光伏组串2",3,0.0705f,"2021-11-08")
)

val logTypes = listOf("综合报表","设备健康指数报表","逆变器状态信息表")

val projects = listOf(
    ProjectInformationModel("长阳创谷E楼屋顶",40,5,35,12.5f,0.75f, listOf(
        ConverterStatusModel("逆变器1A",0.9520f, listOf(
            StreamStatusModel("汇流箱01", listOf(85,85,85,85,85,60),"正常")
        ),"5:59","18:30"),
        ConverterStatusModel("逆变器1B",0.9520f, listOf(
            StreamStatusModel("汇流箱01", listOf(85,85,85,85,85,60),"正常")
        ),"5:59","18:30")
    )),
    ProjectInformationModel("长阳创谷C楼屋顶",40,5,35,12.5f,0.75f, listOf(
        ConverterStatusModel("逆变器1A",0.9520f, listOf(
            StreamStatusModel("汇流箱01", listOf(85,85,85,85,85,60),"正常")
        ),"5:59","18:30"),
        ConverterStatusModel("逆变器1B",0.9520f, listOf(
            StreamStatusModel("汇流箱01", listOf(85,85,85,85,85,60),"正常")
        ),"5:59","18:30")
    ))
)

val messageList = listOf(
    MessageModel(1,"屋顶** 1A逆变器通讯不佳", dateFormatter.format(Date()),true),
    MessageModel(2,"屋顶** 07组串部分遮挡",dateFormatter.format(Date()),false),
    MessageModel(3,"屋顶** 1A逆变器通讯不佳","2021-10-8",false),
    MessageModel(4,"屋顶** 07组串部分遮挡","2021-10-8",false),
    MessageModel(5,"屋顶**","2021-10-8",true),
    MessageModel(6,"屋顶**","2021-10-8",false),
    MessageModel(7,"屋顶**","2021-10-8",false),
    MessageModel(8,"屋顶**","2021-10-8",true),
    MessageModel(9,"屋顶**","2021-10-8",false),
    MessageModel(10,"屋顶**","2021-10-8",false),
    MessageModel(11,"屋顶**","2021-10-8",false),
    MessageModel(12,"屋顶**","2021-10-8",false),
    MessageModel(13,"屋顶**","2021-10-8",true),
    MessageModel(14,"屋顶**","2021-10-8",false),
    MessageModel(15,"屋顶**","2021-10-8",false)
)
val attachmentList = listOf("*************.jpg","********.jpg")

val formDataMediaType = "multipart/form-data"