package com.minghua.opratingstate.ui.fragments.project_information

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.minghua.opratingstate.R

@Composable
fun CapacityInformation() {
    Row(horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.ic_capacity),
                contentDescription = null,
                modifier = Modifier.size(45.dp)
            )
            Column(verticalArrangement = Arrangement.spacedBy(6.dp, Alignment.Top)) {
                Text(text = "装机容量", style = TextStyle(fontSize = 16.sp))
                Text(text = "12kWp")
            }
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.ic_conbine_time),
                contentDescription = null,
                modifier = Modifier.size(45.dp)
            )
            Column(verticalArrangement = Arrangement.spacedBy(6.dp, Alignment.Top)) {
                Text(text = "投运时间", style = TextStyle(fontSize = 16.sp))
                Text(text = "2021-01-21")
            }
        }
    }
}