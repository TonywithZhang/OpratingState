package com.minghua.opratingstate.ui.fragments.log_page

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.minghua.opratingstate.R

@Composable
fun LogPageItem(
    @DrawableRes imageId: Int,
    title: String,
    value: String,
    modifier: Modifier = Modifier,
    showIcon: Boolean = true
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(3.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (showIcon){
                Image(
                    painter = painterResource(id = imageId),
                    contentDescription = null,
                    modifier = Modifier.size(22.dp)
                )
            }
            Text(text = title,style = TextStyle(fontSize = 12.sp))
        }
        Text(text = value,style = TextStyle(fontSize = 12.sp))
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLogPageItem() {
    LogPageItem(
        imageId = R.drawable.ic_device,
        title = "发电量（kWh）",
        value = "35",
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    )
}