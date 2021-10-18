package com.minghua.opratingstate.ui.drawings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.minghua.opratingstate.R
import com.minghua.opratingstate.models.ProjectItemModel

@Composable
fun DetailProjectItem(model: ProjectItemModel){
    Row(horizontalArrangement = Arrangement.Start,verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.background(
            Color.White).fillMaxWidth()
    ) {
        Image(painter = painterResource(id = R.drawable.ic_device), contentDescription = null,Modifier.size(80.dp).padding(5.dp))
        Column(Modifier.padding(5.dp)) {
            Text(text = model.name,style = MaterialTheme.typography.h4)
            Text(text = "装机容量：${model.capacity}kWh")
            Text(text = "逆变器：${model.converter}")
        }
    }
}