package com.minghua.opratingstate.network.repositories

import com.minghua.opratingstate.network.service.LoginService
import com.minghua.opratingstate.utils.AutoCookieJar
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object LoginRepository {
    private val retrofit: Retrofit = Retrofit.Builder().baseUrl("http://101.132.236.192:8079/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClient.Builder().cookieJar(AutoCookieJar).build())
        .build()
    fun create(): LoginService = retrofit.create(LoginService ::class.java)
}

fun loginNetwork() = LoginRepository.create()