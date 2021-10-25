package com.minghua.opratingstate.ui.fragments.project_information

import android.graphics.fonts.FontStyle
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.minghua.opratingstate.R

@Composable
fun WeatherCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .aspectRatio(1.618f),
        elevation = 10.dp,
        shape = RoundedCornerShape(15)
    ) {
        Image(
            painter = painterResource(id = R.drawable.card_back),
            contentDescription = "卡片背景图片",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillHeight
        )
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxSize().background(Color(0x88888888)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                horizontalArrangement = Arrangement.Start, modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_sunny),
                    contentDescription = null,
                    modifier = Modifier.size(25.dp),
                    tint = Color.White
                )
                Text("晴，32℃", color = Color.White, style = MaterialTheme.typography.subtitle1)
            }
            Column(verticalArrangement = Arrangement.spacedBy(5.dp),horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "今日发电（kWh）",
                    color = Color.White
                )
                Text(
                    text = "20",
                    style = TextStyle.Default.copy(
                        fontSize = MaterialTheme.typography.subtitle1.fontSize,
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                    ),
                    color = Color.White
                )
            }
            Row(horizontalArrangement = Arrangement.SpaceBetween,modifier = Modifier.fillMaxWidth()) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(3.dp)
                ) {
                    Text(text = "当月发电（kWh）",color = Color.White)
                    Text(text = "150.5",color = Color.White)
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(3.dp)
                ) {
                    Text(text = "累计发电（kWh）",color = Color.White)
                    Text(text = "2100.43",color = Color.White)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewWeatherCard() {
    androidx.compose.material.Surface {
        WeatherCard()
    }
}