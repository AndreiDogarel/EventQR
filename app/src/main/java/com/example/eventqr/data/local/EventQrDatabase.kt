package com.example.eventqr.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.eventqr.data.local.dao.ClientDao
import com.example.eventqr.data.local.entity.ClientEntity

@Database(
    entities = [ClientEntity::class],
    version = 1,
    exportSchema = true
)
abstract class EventQrDatabase : RoomDatabase() {
    abstract fun clientDao(): ClientDao
}
