package com.minghua.opratingstate.network.service

import com.minghua.opratingstate.models.LocalRoofStateModel
import com.minghua.opratingstate.models.LocalRoofSum
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface LocalRoofStateService {
    @GET("local_message")
    suspend fun localRoofStates(@Query("time")time : String?): LocalRoofSum
}