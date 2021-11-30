package com.karta.api

data class ErrorResponse(
    val timestamp: String,
    val status: String,
    val error: String,
    val path: String
)
