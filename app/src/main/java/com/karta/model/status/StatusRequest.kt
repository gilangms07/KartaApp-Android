package com.karta.model.status

import com.google.gson.annotations.SerializedName

data class StatusRequest (
    @SerializedName("id")
    val id: Long? = null,
    @SerializedName("name_ketua")
    val nameKetua: String,
    @SerializedName("name_wakil")
    val nameWakil: String,
    @SerializedName("rt")
    val rt: String,
    @SerializedName("status")
    val status: String
)