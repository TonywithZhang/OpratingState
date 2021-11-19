package com.minghua.opratingstate.network.service

import com.minghua.opratingstate.models.WeatherModel
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("weather_mini")
    suspend fun weatherToday(@Query("city")cityName : String) : WeatherModel
}