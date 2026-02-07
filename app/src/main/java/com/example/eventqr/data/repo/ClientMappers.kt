package com.example.eventqr.data.repo

import com.example.eventqr.data.local.entity.ClientEntity
import com.example.eventqr.domain.model.Client
import com.example.eventqr.domain.model.ClientType

fun ClientEntity.toDomain(): Client {
    val type = runCatching { ClientType.valueOf(clientType) }.getOrElse { ClientType.DCS_EMPLOYEE }
    return Client(
        id = id,
        firstName = firstName,
        lastName = lastName,
        clientType = type,
        company = company,
        qrRaw = qrRaw,
        scannedAt = scannedAt
    )
}

fun Client.toEntity(): ClientEntity {
    return ClientEntity(
        id = id,
        firstName = firstName,
        lastName = lastName,
        clientType = clientType.name,
        company = company,
        qrRaw = qrRaw,
        scannedAt = scannedAt
    )
}
