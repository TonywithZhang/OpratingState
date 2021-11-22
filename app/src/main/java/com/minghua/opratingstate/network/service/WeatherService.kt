package com.minghua.opratingstate.network.service

import com.minghua.opratingstate.models.WeatherModel
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("now")
    suspend fun weatherToday(
        @Query("key")key : String = "515675e5425a42df83d2611575d0ff5e",
        @Query("location")cityName : String
    ) : WeatherModel
}