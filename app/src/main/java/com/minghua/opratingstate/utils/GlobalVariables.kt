package com.minghua.opratingstate.utils

import androidx.compose.ui.graphics.Color
import com.minghua.opratingstate.ui.theme.Teal200
import java.text.SimpleDateFormat
import java.util.*

val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).apply {
    timeZone = TimeZone.getTimeZone("Asia/Shanghai")
}

fun xCoordination(width: Float, x: Float, totalCount: Int): Float {
    return x * (width * 1.0 / totalCount).toFloat()
}

fun yCoordination(height: Float, y: Float, maxValue: Float): Float {
    return height - y * (height / maxValue) * 0.95f
}

val colorGroup = listOf(Color.Red, Teal200)