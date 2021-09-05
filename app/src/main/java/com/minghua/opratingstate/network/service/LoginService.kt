package com.minghua.opratingstate.network.service

import com.minghua.opratingstate.models.LoginModel
import retrofit2.http.POST
import retrofit2.http.Query

interface LoginService {
    @POST("Login")
    suspend fun login(@Query("UserName")userName: String,@Query("Password")password: String): LoginModel
}