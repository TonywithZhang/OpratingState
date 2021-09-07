package com.minghua.opratingstate.network.repositories

import com.minghua.opratingstate.network.service.LocalRoofStateService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object LocalRoofStateReepository {
    val retrofit = Retrofit.Builder()
        .client(OkHttpClient())
        .baseUrl("http://101.132.236.192:8079/local/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    fun create() = retrofit.create(LocalRoofStateService ::class.java)
}

fun localRoofRepo() = LocalRoofStateReepository.create()