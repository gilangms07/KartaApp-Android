package com.karta.model.register

import com.google.gson.annotations.SerializedName

data class RegisterResponse (
        @SerializedName("id")
        val id: Long,
        @SerializedName("name")
        val name: String,
        @SerializedName("email")
        val email: String,
        @SerializedName("address")
        val address: String,
        @SerializedName("rt")
        val rt: String,
        @SerializedName("rw")
        val rw: String,
        @SerializedName("password")
        val password: String

        )