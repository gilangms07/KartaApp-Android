package com.karta.model.tambah

import com.google.gson.annotations.SerializedName

data class TambahRequest (
        @SerializedName("name")
        val name: String,
        @SerializedName("address")
        val address: String,
        @SerializedName("rt")
        val rt: String,
        @SerializedName("rw")
        val rw: String,
        @SerializedName("email")
        val email: String,
        @SerializedName("phone_number")
        val phoneNumber: String,
        )