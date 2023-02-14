package com.karta.admin

import com.google.gson.annotations.SerializedName

data class AdminRequest(
    @SerializedName("id")
    val id: Long? = null,
    @SerializedName("name_admin")
    val nameAdmin: String,
    @SerializedName("email_admin")
    val emailAdmin: String
)