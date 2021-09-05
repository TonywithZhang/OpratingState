package com.minghua.opratingstate.network.repositories

import com.minghua.opratingstate.network.service.LoginService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object LoginRepository {
    private val retrofit: Retrofit = Retrofit.Builder().baseUrl("http://101.132.236.192:8001/Account/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClient())
        .build()
    fun create(): LoginService = retrofit.create(LoginService ::class.java)
}

fun loginNetwork() = LoginRepository.create()