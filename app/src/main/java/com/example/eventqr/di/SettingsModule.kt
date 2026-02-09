package com.example.eventqr.di

import android.content.Context
import com.example.eventqr.data.settings.DataStoreSettingsRepository
import com.example.eventqr.data.settings.SettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SettingsModule {

    @Provides
    @Singleton
    fun provideSettingsRepository(@ApplicationContext context: Context): SettingsRepository {
        return DataStoreSettingsRepository(context)
    }
}
