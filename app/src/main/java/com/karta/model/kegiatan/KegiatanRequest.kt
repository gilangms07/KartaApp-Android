package com.karta.model.kegiatan

import com.google.gson.annotations.SerializedName

data class KegiatanRequest(
        @SerializedName("id")
        val id: Long,
        @SerializedName("photo")
        val photo: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("description")
        val description: String
)