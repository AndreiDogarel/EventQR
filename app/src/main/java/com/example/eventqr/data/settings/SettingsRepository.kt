package com.example.eventqr.data.settings

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    val darkMode: Flow<Boolean>
    suspend fun setDarkMode(enabled: Boolean)
}
