package com.karta.api

import com.karta.model.delete.DeleteResponse
import com.karta.model.edit.EditResponse
import com.karta.model.iuran.IuranRequest
import com.karta.model.iuran.IuranResponse
import com.karta.model.kegiatan.KegiatanRequest
import com.karta.model.kegiatan.KegiatanResponse
import com.karta.model.login.LoginRequest
import com.karta.model.login.LoginResponse
import com.karta.model.register.RegisterRequest
import com.karta.model.register.RegisterResponse
import com.karta.model.status.StatusRequest
import com.karta.model.status.StatusResponse
import com.karta.model.tambah.TambahRequest
import com.karta.model.tambah.TambahResponse
import com.karta.model.tampil.TampilResponse
import com.karta.model.user.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface UserService {
    @POST("/api/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST("/api/register")
    fun register(@Body request: RegisterRequest): Call<RegisterResponse>

    @POST("/api/add_member")
    fun tambah(@Body request: TambahRequest): Call<TambahResponse>

    @POST("/api/add_status")
    fun tambahstatus(@Body request: StatusRequest): Call<StatusResponse>

    @GET("/api/all_member")
    fun tampil(): Call<List<TampilResponse>>

    @GET("/api/all_status")
    fun status(): Call<List<StatusResponse>>

    @POST("/api/delete_member")
    fun delete(@Body request: TampilResponse): Call<DeleteResponse>

    @DELETE("/api/delete_status")
    fun deletestatus(@Query("id") id: Long) : Call<DeleteResponse>

    @PUT("/api/update_member")
    fun update(@Body request: TampilResponse): Call<EditResponse>

    @PUT("/api/update_status")
    fun updatestatus(@Body request: StatusRequest): Call<StatusResponse>

    @GET("/api/get_profile")
    fun getUser(@Query("email") email: String): Call<UserResponse>

    @POST("/api/create_collective")
    fun buatIuran(@Body request: IuranRequest): Call<IuranResponse>

    @GET("/api/get_collective_list")
    fun daftarIuran(): Call<List<IuranResponse>>

    @POST("/api/create_activity")
    fun buatKegiatan(@Body request: KegiatanRequest): Call<KegiatanResponse>

    @GET("/api/get_activity_list")
    fun daftarKegiatan(): Call<List<KegiatanResponse>>

    @DELETE("/api/delete_activity")
    fun hapusKegiatan(@Query("id") id: Long): Call<ActivityResponse>

    @PUT("/api/aktivasi_user")
    fun aktivasiuser(@Query("id") id: Long): Call<UserResponse>
}
