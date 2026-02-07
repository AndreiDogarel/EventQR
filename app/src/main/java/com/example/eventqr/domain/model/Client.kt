package com.example.eventqr.domain.model

data class Client(
    val id: String,
    val firstName: String,
    val lastName: String,
    val clientType: ClientType,
    val company: String,
    val qrRaw: String,
    val scannedAt: Long
)
