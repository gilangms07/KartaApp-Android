package com.karta.api

import com.karta.model.login.LoginRequest
import com.karta.model.login.LoginResponse
import com.karta.model.register.RegisterRequest
import com.karta.model.register.RegisterResponse
import com.karta.model.tambah.TambahRequest
import com.karta.model.tambah.TambahResponse
import com.karta.model.tampil.TampilResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserService {
    @POST("/api/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST("/api/register")
    fun register(@Body request: RegisterRequest): Call<RegisterResponse>

    @POST("/api/tambah")
    fun tambah(@Body request: TambahRequest): Call<TambahResponse>

    @GET("/api/tampil")
    fun tampil(): Call<List<TampilResponse>>
}