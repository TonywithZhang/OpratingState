package com.minghua.opratingstate.network.service

import com.minghua.opratingstate.models.BarChartDataModel
import com.minghua.opratingstate.models.LocalRoofStateModel
import com.minghua.opratingstate.models.LocalRoofSum
import com.minghua.opratingstate.models.OutputRadiationModel
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
}