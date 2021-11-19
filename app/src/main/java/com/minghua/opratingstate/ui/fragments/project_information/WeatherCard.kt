package com.minghua.opratingstate.ui.fragments.project_information

import android.graphics.fonts.FontStyle
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.minghua.opratingstate.R
import com.minghua.opratingstate.network.repositories.localRoofRepo
import com.minghua.opratingstate.network.repositories.weatherService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x88888888)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var todayProduction by remember{ mutableStateOf("20")}
            var monthProduction by remember{ mutableStateOf("150.5")}
            var totalProduction by remember{ mutableStateOf("2100.4")}

            LaunchedEffect(key1 = 0)
            {
                withContext(Dispatchers.IO)
                {
                    val result = localRoofRepo().getWeatherCardData()
                    withContext(Dispatchers.Main)
                    {
                        todayProduction = String.format("%.1f",result.day)
                        monthProduction = String.format("%.1f",result.month)
                        totalProduction = String.format("%.1f",result.total)
                    }
                }
            }
            Row(
                horizontalArrangement = Arrangement.Start, modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, top = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                var weatherIcon by remember { mutableStateOf(R.drawable.ic_sunny) }
                var weather by remember { mutableStateOf("晴，32℃") }
                LaunchedEffect(key1 = 0)
                {
                    withContext(Dispatchers.IO)
                    {
                        val weatherData = weatherService().weatherToday("杨浦区")
                        val weatherToday = weatherData.data.forecast[0]
                        withContext(Dispatchers.Main) {
                            weather = weatherToday.type + weatherToday.low.substring(3) + " ~ ${weatherToday.high.substring(3)}"
                            weatherIcon = when{
                                weatherToday.type.contains("晴") -> R.drawable.ic_qingtian
                                weatherToday.type.contains("阴") -> R.drawable.ic_yin
                                weatherToday.type.contains("雨") -> R.drawable.ic_dayu
                                weatherToday.type.contains("云") -> R.drawable.ic_duoyun
                                weatherToday.type.contains("雾") -> R.drawable.ic_wu
                                weatherToday.type.contains("雪") -> R.drawable.ic_daxue
                                else -> R.drawable.ic_wu
                            }
                        }
                    }
                }
                Icon(
                    painter = painterResource(id = weatherIcon),
                    contentDescription = null,
                    modifier = Modifier.size(25.dp),
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(weather, color = Color.White, style = MaterialTheme.typography.subtitle1)
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "今日发电（kWh）",
                    style = TextStyle(color = Color.White, fontSize = 18.sp)
                )
                Text(
                    text = todayProduction,
                    style = TextStyle(color = Color.White, fontSize = 24.sp)
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(3.dp),
                    modifier = Modifier.padding(bottom = 6.dp)
                ) {
                    Text(text = "当月发电（kWh）", color = Color.White)
                    Text(text = monthProduction, color = Color.White)
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(3.dp)
                ) {
                    Text(text = "累计发电（kWh）", color = Color.White)
                    Text(text = totalProduction, color = Color.White)
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