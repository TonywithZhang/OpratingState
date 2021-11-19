package com.minghua.opratingstate.network.repositories

import com.minghua.opratingstate.network.service.LocalRoofStateService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object LocalRoofStateRepository {
    private val retrofit: Retrofit = Retrofit.Builder()
        .client(OkHttpClient())
        .baseUrl("http://101.132.236.192:8079/local/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    fun create(): LocalRoofStateService = retrofit.create(LocalRoofStateService ::class.java)
}

fun localRoofRepo(): LocalRoofStateService = LocalRoofStateRepository.create()