package com.minghua.opratingstate.models

//data class WeatherModel(
//    val `data`: Data,
//    val desc: String,
//    val status: Int
//)
//
//data class Data(
//    val city: String,
//    val forecast: List<Forecast>,
//    val ganmao: String,
//    val wendu: String,
//    val yesterday: Yesterday
//)
//
//data class Forecast(
//    val date: String,
//    val fengli: String,
//    val fengxiang: String,
//    val high: String,
//    val low: String,
//    val type: String
//)
//
//data class Yesterday(
//    val date: String,
//    val fl: String,
//    val fx: String,
//    val high: String,
//    val low: String,
//    val type: String
//)

data class WeatherModel(
    val code: String,
    val fxLink: String,
    val now: Now,
    val refer: Refer,
    val updateTime: String
)

data class Now(
    val cloud: String,
    val dew: String,
    val feelsLike: String,
    val humidity: String,
    val icon: String,
    val obsTime: String,
    val precip: String,
    val pressure: String,
    val temp: String,
    val text: String,
    val vis: String,
    val wind360: String,
    val windDir: String,
    val windScale: String,
    val windSpeed: String
)

data class Refer(
    val license: List<String>,
    val sources: List<String>
)