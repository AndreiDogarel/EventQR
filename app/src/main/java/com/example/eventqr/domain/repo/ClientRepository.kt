package com.example.eventqr.domain.repo

import com.example.eventqr.domain.model.Client
import kotlinx.coroutines.flow.Flow

interface ClientRepository {
    suspend fun addClientIgnoreDuplicate(client: Client): Boolean
    fun observeAll(): Flow<List<Client>>
    fun observeByType(type: String): Flow<List<Client>>
    fun observeByCompany(company: String): Flow<List<Client>>
    fun observeByTypeAndCompany(type: String, company: String): Flow<List<Client>>
}
