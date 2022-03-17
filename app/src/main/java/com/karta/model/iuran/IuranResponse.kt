package com.karta.model.iuran

import com.google.gson.annotations.SerializedName

class IuranResponse(
        @SerializedName("id")
        val id: Long,
        @SerializedName("name")
        val name: String,
        @SerializedName("month")
        val month: String,
        @SerializedName("status")
        val status: String,
)