package com.minghua.opratingstate.ui.fragments.project_information

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.minghua.opratingstate.ui.theme.topBarColor

@Composable
fun EfficiencyLoss(){
    Column(modifier = Modifier.fillMaxWidth()) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(35.dp).background(topBarColor)){
            Text(
                text = "光伏组件电功率损失率", style = MaterialTheme.typography.h6, modifier = Modifier.align(
                    Alignment.Center
                )
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEfficiencyLoss(){
    EfficiencyLoss()
}