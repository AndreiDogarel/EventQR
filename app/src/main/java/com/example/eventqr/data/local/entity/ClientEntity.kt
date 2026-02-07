package com.example.eventqr.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "clients",
    indices = [
        Index(value = ["qrRaw"], unique = true),
        Index(value = ["clientType"]),
        Index(value = ["company"]),
        Index(value = ["scannedAt"])
    ]
)
data class ClientEntity(
    @PrimaryKey val id: String,
    val firstName: String,
    val lastName: String,
    val clientType: String,
    val company: String,
    val qrRaw: String,
    val scannedAt: Long
)
