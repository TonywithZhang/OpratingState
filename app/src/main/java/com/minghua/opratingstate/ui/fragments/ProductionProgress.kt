package com.minghua.opratingstate.ui.fragments

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.minghua.opratingstate.ui.drawings.ProgressCircle
import com.minghua.opratingstate.ui.drawings.StatisticsPresenter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProductionProgress(name: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        StatisticsPresenter()
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            ProgressCircle(progress = 0.15f, modifier = Modifier.size(50.dp))
            Column(verticalArrangement = Arrangement.spacedBy(3.dp),horizontalAlignment = Alignment.Start) {
                Text(text = "年发电量/年计划发电量（万kWh）")
                Text(text = "6.45/200")
                Text(text = "年利用小时数/年计划利用小时数（h）")
                Text(text = "200/1200")
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PreviewProductionProgress() {
    ProductionProgress("长阳创谷E栋屋顶")
}