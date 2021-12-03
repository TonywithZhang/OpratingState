package com.minghua.opratingstate.network.repositories

import com.minghua.opratingstate.network.service.WeatherService
import com.minghua.opratingstate.utils.AutoCookieJar
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WeatherRepository {
    private val retrofit = Retrofit.Builder()
        .client(OkHttpClient.Builder().cookieJar(AutoCookieJar).build())
        .baseUrl("https://devapi.qweather.com/v7/weather/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    fun create(): WeatherService = retrofit.create(WeatherService :: class.java)
}

fun weatherService() = WeatherRepository.create()