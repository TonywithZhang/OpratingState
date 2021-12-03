package com.minghua.opratingstate.network.repositories

import com.minghua.opratingstate.network.service.LocalRoofStateService
import com.minghua.opratingstate.utils.AutoCookieJar
import me.jessyan.progressmanager.ProgressManager
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object LocalRoofStateRepository {

    private val retrofit: Retrofit = Retrofit.Builder()
        .client(ProgressManager.getInstance().with(OkHttpClient.Builder().cookieJar(AutoCookieJar)).build())
        .baseUrl("http://101.132.236.192:8079/local/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    fun create(): LocalRoofStateService = retrofit.create(LocalRoofStateService ::class.java)
}

fun localRoofRepo(): LocalRoofStateService = LocalRoofStateRepository.create()