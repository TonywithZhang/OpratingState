package com.minghua.opratingstate.ui.fragments.log_page

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.minghua.opratingstate.R
import com.minghua.opratingstate.utils.projects

@Composable
fun SummaryReport() {
    val fraction = 0.45f
    Column(modifier = Modifier.fillMaxWidth()) {
        projects.forEach {
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
                Text(
                    text = it.name,
                    style = TextStyle(fontWeight = FontWeight.Bold)
                )
                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 5.dp),
                    mainAxisAlignment = FlowMainAxisAlignment.SpaceAround,
                    crossAxisSpacing = 5.dp
                ) {
                    LogPageItem(
                        imageId = R.drawable.radiation_quantity,
                        title = "累计辐照量（W/m²）",
                        value = it.radiation.toString(),
                        modifier = Modifier.fillMaxWidth(fraction)
                    )
                    LogPageItem(
                        imageId = R.drawable.ic_24h,
                        title = "等效利用小时数（h）",
                        value = it.equationHours.toString(),
                        modifier = Modifier.fillMaxWidth(fraction)
                    )
                    LogPageItem(
                        imageId = R.drawable.ic_production,
                        title = "发电量（kWh）",
                        value = it.production.toString(),
                        modifier = Modifier.fillMaxWidth(fraction)
                    )
                    LogPageItem(
                        imageId = R.drawable.ic_day_radiation,
                        title = "日照时间（h）",
                        value = String.format("%.2f", it.radiationTime),
                        modifier = Modifier.fillMaxWidth(fraction)
                    )
                    LogPageItem(
                        imageId = R.drawable.white,
                        title = "PR",
                        value = String.format("%.1f%%",it.pr * 100),
                        modifier = Modifier.fillMaxWidth(fraction)
                    )
                    LogPageItem(
                        imageId = R.drawable.ic_device,
                        title = "  ",
                        value = "  ",
                        modifier = Modifier.fillMaxWidth(fraction),
                        showIcon = false
                    )
                }
            }
        }
    }
    FlowRow(modifier = Modifier.fillMaxWidth()) {

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSummaryReport() {
    SummaryReport()
}