package com.example.eventqr.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.eventqr.ui.navigation.AppNavHost

@Composable
fun AppRoot() {
    val startupVm: StartupViewModel = hiltViewModel()
    val ready = startupVm.ready.collectAsState()

    MaterialTheme {
        Surface {
            if (ready.value) {
                AppNavHost()
            } else {
                Text("Signing in...")
            }
        }
    }
}
