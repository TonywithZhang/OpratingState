package com.minghua.opratingstate.ui.fragments.project_information

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.minghua.opratingstate.R

@Composable
fun LocationInformation(location: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier

            .clip(RoundedCornerShape(50))
            .background(Color.White).padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(painterResource(id = R.drawable.ic_location),contentDescription = null,Modifier.size(25.dp))
        Text(location)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLocationInformation(){
    LocationInformation(location = "中国上海市杨浦区长阳创谷E栋")
}