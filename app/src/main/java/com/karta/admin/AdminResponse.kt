package com.karta.admin

import com.google.gson.annotations.SerializedName
import com.karta.model.login.LoginResponse

data class AdminResponse(
    @SerializedName("data")
    val data: List<LoginResponse>
)
