package com.karta.model.tampil

import com.google.gson.annotations.SerializedName

data class TampilResponse (
        @SerializedName("id")
        val id: Long,
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
        @SerializedName("phoneNumber")
        val phoneNumber: String

        )