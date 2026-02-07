package com.example.eventqr.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.eventqr.data.local.entity.ClientEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ClientDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertIgnore(entity: ClientEntity): Long

    @Query("SELECT * FROM clients ORDER BY scannedAt DESC")
    fun observeAll(): Flow<List<ClientEntity>>

    @Query("SELECT * FROM clients WHERE clientType = :type ORDER BY scannedAt DESC")
    fun observeByType(type: String): Flow<List<ClientEntity>>

    @Query("SELECT * FROM clients WHERE company = :company ORDER BY scannedAt DESC")
    fun observeByCompany(company: String): Flow<List<ClientEntity>>

    @Query("SELECT * FROM clients WHERE clientType = :type AND company = :company ORDER BY scannedAt DESC")
    fun observeByTypeAndCompany(type: String, company: String): Flow<List<ClientEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM clients WHERE qrRaw = :qrRaw)")
    suspend fun existsByQrRaw(qrRaw: String): Boolean
}
