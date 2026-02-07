package com.example.eventqr.di

import android.content.Context
import androidx.room.Room
import com.example.eventqr.data.local.EventQrDatabase
import com.example.eventqr.data.local.dao.ClientDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): EventQrDatabase {
        return Room.databaseBuilder(
            context,
            EventQrDatabase::class.java,
            "eventqr.db"
        ).build()
    }

    @Provides
    fun provideClientDao(db: EventQrDatabase): ClientDao = db.clientDao()
}
