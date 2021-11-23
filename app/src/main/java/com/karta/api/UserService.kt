package com.karta.api

import com.karta.model.login.LoginRequest
import com.karta.model.login.LoginResponse
import com.karta.model.register.RegisterRequest
import com.karta.model.register.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {
    @POST("/api/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST("/api/register")
    fun register(@Body request: RegisterRequest): Call<RegisterResponse>
}