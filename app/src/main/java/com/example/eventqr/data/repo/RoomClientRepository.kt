package com.example.eventqr.data.repo

import com.example.eventqr.data.local.dao.ClientDao
import com.example.eventqr.domain.model.Client
import com.example.eventqr.domain.repo.ClientRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoomClientRepository @Inject constructor(
    private val dao: ClientDao
) : ClientRepository {

    override suspend fun addClientIgnoreDuplicate(client: Client): Boolean {
        val rowId = dao.insertIgnore(client.toEntity())
        return rowId != -1L
    }

    override fun observeAll(): Flow<List<Client>> =
        dao.observeAll().map { list -> list.map { it.toDomain() } }

    override fun observeByType(type: String): Flow<List<Client>> =
        dao.observeByType(type).map { list -> list.map { it.toDomain() } }

    override fun observeByCompany(company: String): Flow<List<Client>> =
        dao.observeByCompany(company).map { list -> list.map { it.toDomain() } }

    override fun observeByTypeAndCompany(type: String, company: String): Flow<List<Client>> =
        dao.observeByTypeAndCompany(type, company).map { list -> list.map { it.toDomain() } }
}
