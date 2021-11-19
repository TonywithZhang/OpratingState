package com.minghua.opratingstate.network.service

import com.minghua.opratingstate.models.*
import retrofit2.http.*

interface LocalRoofStateService {
    @GET("local_message")
    suspend fun localRoofStates(@Query("time") time: String?): LocalRoofSum

    @GET("local_power")
    suspend fun localRoofPower(): LocalRoofStateModel

    @POST("production")
    suspend fun timeSpannedProduction(
        @Query("scope") index: Int,
        @Query("startTime") startTime: String,
        @Query("endTime") endTime: String
    ): List<BarChartDataModel>

    @GET("out_radiation")
    suspend fun getOutputRadiation() : OutputRadiationModel

    @GET("weather_card")
    suspend fun getWeatherCardData() : WeatherCardData
}