package com.karta.model.register

import com.google.gson.annotations.SerializedName

data class RegisterRequest (
        @SerializedName("name")
        val name: String,
        @SerializedName("email")
        val email: String,
        @SerializedName("address")
        val alamat: String,
        @SerializedName("rt")
        val rt: String,
        @SerializedName("rw")
        val rw: String,
        @SerializedName("password")
        val password: String,
        @SerializedName("level")
        val level: Int
        )
