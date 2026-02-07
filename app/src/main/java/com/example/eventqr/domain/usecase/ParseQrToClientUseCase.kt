package com.example.eventqr.domain.usecase

import com.example.eventqr.domain.model.Client
import com.example.eventqr.domain.model.ClientType
import java.util.UUID

class ParseQrToClientUseCase {

    fun parse(qrRaw: String): Client? {
        val trimmed = qrRaw.trim()
        if (trimmed.isEmpty()) return null

        val parts = trimmed.split("|")
        if (parts.size != 4) return null

        val firstName = parts[0].trim()
        val lastName = parts[1].trim()
        val typeStr = parts[2].trim()
        val companyFromQr = parts[3].trim()

        if (firstName.isEmpty()) return null
        if (lastName.isEmpty()) return null
        if (typeStr.isEmpty()) return null

        val type = runCatching { ClientType.valueOf(typeStr) }.getOrElse { return null }

        val company = if (type == ClientType.DCS_EMPLOYEE) "DCS" else companyFromQr
        if (company.isEmpty()) return null

        val now = System.currentTimeMillis()

        return Client(
            id = UUID.randomUUID().toString(),
            firstName = firstName,
            lastName = lastName,
            clientType = type,
            company = company,
            qrRaw = trimmed,
            scannedAt = now
        )
    }
}
