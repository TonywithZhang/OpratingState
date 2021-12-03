package com.minghua.opratingstate.network.service

import com.minghua.opratingstate.models.LoginModel
import retrofit2.http.POST
import retrofit2.http.Query

interface LoginService {
    @POST("login")
    suspend fun login(@Query("username")userName: String,@Query("password")password: String): LoginModel
}