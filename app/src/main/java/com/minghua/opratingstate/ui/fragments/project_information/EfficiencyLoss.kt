package com.minghua.opratingstate.ui.fragments.project_information

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.minghua.opratingstate.ui.theme.stateColors
import com.minghua.opratingstate.ui.theme.topBarColor
import com.minghua.opratingstate.utils.lossSamples

@Composable
fun EfficiencyLoss(name: String){
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        item {
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(35.dp)
                .background(topBarColor)){
                Text(
                    text = "光伏组件电功率损失率", style = MaterialTheme.typography.h6, modifier = Modifier.align(
                        Alignment.Center
                    )
                )
            }
        }
        item {
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Canvas(modifier = Modifier.size(20.dp)){
                    drawRoundRect(stateColors[0], Offset.Zero, Size(20.dp.toPx(),20.dp.toPx()), CornerRadius(5.dp.toPx(),5.dp.toPx()))
                }
                Text(text = "积灰时间")

                Canvas(modifier = Modifier.size(20.dp)){
                    drawRoundRect(stateColors[1], Offset.Zero, Size(20.dp.toPx(),20.dp.toPx()), CornerRadius(5.dp.toPx(),5.dp.toPx()))
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "电功率损失值")
                    Text(text = "（计算值）")
                }
                Canvas(modifier = Modifier.size(20.dp)){
                    drawRoundRect(stateColors[2], Offset.Zero, Size(20.dp.toPx(),20.dp.toPx()), CornerRadius(5.dp.toPx(),5.dp.toPx()))
                }
                Text(text = "建议清理时间")
            }
        }
        items(lossSamples){
            EfficiencyLossItem(name = it.name, time = it.time, loss = it.loss, advisedDate = it.advisedDate)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEfficiencyLoss(){
    EfficiencyLoss("")
}

