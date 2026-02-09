package com.example.eventqr.ui

import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.eventqr.ui.navigation.AppNavHost
import com.example.eventqr.ui.theme.EventQRTheme

@Composable
fun AppRoot() {
    val startupVm: StartupViewModel = hiltViewModel()
    val ready = startupVm.ready.collectAsState()

    val settingsVm: SettingsViewModel = hiltViewModel()
    val darkMode = settingsVm.darkMode.collectAsState()

    EventQRTheme(darkTheme = darkMode.value) {
        Surface {
            if (ready.value) {
                AppNavHost()
            } else {
                Text("Signing in...")
            }
        }
    }
}
