package com.karta.model.delete

import com.google.gson.annotations.SerializedName

data class DeleteResponse (
        @SerializedName("timestamp")
        val timestamp: String,
        @SerializedName("status")
        val status: String,
        @SerializedName("error")
        val error: String,
        @SerializedName("path")
        val path: String,
        )